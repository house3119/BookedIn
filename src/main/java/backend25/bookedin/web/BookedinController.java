package backend25.bookedin.web;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import backend25.bookedin.model.AccountType;
import backend25.bookedin.model.AccountTypeRepository;
import backend25.bookedin.model.AppUser;
import backend25.bookedin.model.AppUserRepository;
import backend25.bookedin.model.ChangePasswordForm;
import backend25.bookedin.model.CountryRepository;
import backend25.bookedin.model.RegisterForm;
import backend25.bookedin.model.ReviewRepository;
import backend25.bookedin.model.UsersBooks;
import backend25.bookedin.model.UsersBooksRepository;
import jakarta.validation.Valid;


@Controller
@EnableMethodSecurity(securedEnabled = true)
public class BookedinController {

  private AppUserRepository appUserRepository;
  private UsersBooksRepository usersBooksRepository;
  private ReviewRepository reviewRepository;
  private CountryRepository countryRepository;
  private AccountTypeRepository accountTypeRepository;

  public BookedinController(AppUserRepository appUserRepository, UsersBooksRepository usersBooksRepository, ReviewRepository reviewRepository, CountryRepository countryRepository, AccountTypeRepository accountTypeRepository) {
    this.appUserRepository = appUserRepository;
    this.usersBooksRepository = usersBooksRepository;
    this.reviewRepository = reviewRepository;
    this.countryRepository = countryRepository;
    this.accountTypeRepository = accountTypeRepository;
  }

  @RequestMapping(value="/login")
  public String login() {	
    return "login";
  }

  @RequestMapping(value="/register", method = RequestMethod.GET)
  public String getRegisterForm(Model model) {
    model.addAttribute("registerForm", new RegisterForm());
    return "register";
  }

  @RequestMapping(value="/register", method = RequestMethod.POST)
  public String saveUser(@Valid RegisterForm registerform, BindingResult bindingResult, Model model) {
    String username = registerform.getUsername();
    if (appUserRepository.findByUsernameIgnoreCase(registerform.getUsername()) != null) {
      bindingResult.rejectValue("username", "err.username", "Username already exists!"); 
    }

    if (!registerform.getPassword1().equals(registerform.getPassword2())) {
      bindingResult.rejectValue("password2", "err.password2", "Passwords don't match!"); 
    }

    if (bindingResult.hasErrors()) {
      model.addAttribute("registerForm", registerform);
      return "register";
    }

    String password = registerform.getPassword1();
    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
    String pwHash = bc.encode(password);
    AccountType acc = accountTypeRepository.findByType("USER");

    AppUser newUser = new AppUser(username, pwHash, acc);
    appUserRepository.save(newUser);

    model.addAttribute("message", "New account created succesfully. You can now log in.");
    return "login";
  } 

  @RequestMapping(value="users/{username}", method=RequestMethod.GET)
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public String getUserProfile(@PathVariable(required = true) String username, Model model, Authentication authentication) {
      AppUser profile = appUserRepository.findByUsernameIgnoreCase(username);

      if (profile == null) {
        return "redirect:/index";
      }

      model.addAttribute("followers", profile.getFollowers().size());

      model.addAttribute("library",usersBooksRepository.findByUser(profile));

      AppUser user = appUserRepository.findByUsernameIgnoreCase(authentication.getName());
      model.addAttribute("user", user);

      model.addAttribute("profile", profile);

      List<AppUser> allUsers = (List<AppUser>) appUserRepository.findAll();
      List<AppUser> withoutLoggedInUser = allUsers.stream().filter(uzer -> uzer.getUsername() != user.getUsername()).collect(Collectors.toList());
      List<String> namesForSearch = withoutLoggedInUser.stream().map(uzer -> uzer.getUsername()).collect(Collectors.toList());
      model.addAttribute("usernamesForSearch", namesForSearch);
      return "profile";
  }

  @RequestMapping(value="users/{username}/edit", method=RequestMethod.GET)
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public String getEditProfilePage(@PathVariable(required = true) String username, Model model, Authentication authentication) {
    AppUser profile = appUserRepository.findByUsernameIgnoreCase(username);
    AppUser loggedInUser = appUserRepository.findByUsernameIgnoreCase(authentication.getName());
    boolean access = true;

    if (profile == null) {
      return "redirect:/index";
    }

    if (profile.getUsername() != loggedInUser.getUsername()) {
      access = false;
    }

    if (loggedInUser.getAccount_type().getAccount_type() == "ADMIN") {
      access = true;
    }

    if (access == false) {
      return "redirect:/index";
    }

    model.addAttribute("profile", profile);
    model.addAttribute("user", loggedInUser);
    model.addAttribute("countries", countryRepository.findAll());

    List<AppUser> allUsers = (List<AppUser>) appUserRepository.findAll();
    List<AppUser> withoutLoggedInUser = allUsers.stream().filter(uzer -> uzer.getUsername() != loggedInUser.getUsername()).collect(Collectors.toList());
    List<String> namesForSearch = withoutLoggedInUser.stream().map(uzer -> uzer.getUsername()).collect(Collectors.toList());
    model.addAttribute("usernamesForSearch", namesForSearch);

    return "editprofile";

  }

  @RequestMapping(value="users/{username}", method=RequestMethod.POST)
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public String saveEditProfile(@Valid @ModelAttribute("profile") AppUser editedUser, BindingResult bindingResult, @PathVariable(required = false) String username, Model model, Authentication authentication) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("profile", editedUser);
      model.addAttribute("user", appUserRepository.findByUsernameIgnoreCase(authentication.getName()));
      model.addAttribute("countries", countryRepository.findAll());
      return "editprofile";
    }

    AppUser oldUser = appUserRepository.findByUsernameIgnoreCase(username);
    oldUser.setAvatar_url(editedUser.getAvatar_url());
    oldUser.setAge(editedUser.getAge());
    oldUser.setCountry(editedUser.getCountry());
    appUserRepository.save(oldUser);

    return "redirect:/users/" + editedUser.getUsername();
  }

  @RequestMapping(value="users/{username}/changepw", method=RequestMethod.GET)
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public String getPwChangeForm(@PathVariable(required = true) String username, Model model, Authentication authentication) {

    if (!username.toLowerCase().equals(authentication.getName().toLowerCase())) {
      return "redirect:/index";
    }
    model.addAttribute("changePwForm", new ChangePasswordForm());
    model.addAttribute("user", appUserRepository.findByUsernameIgnoreCase(username));

    List<AppUser> allUsers = (List<AppUser>) appUserRepository.findAll();
    List<AppUser> withoutLoggedInUser = allUsers.stream().filter(uzer -> uzer.getUsername() != username).collect(Collectors.toList());
    List<String> namesForSearch = withoutLoggedInUser.stream().map(uzer -> uzer.getUsername()).collect(Collectors.toList());
    model.addAttribute("usernamesForSearch", namesForSearch);

    return "changepw";

  }

  @RequestMapping(value="users/{username}/changepw", method=RequestMethod.POST)
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public String updatePw(@Valid @ModelAttribute("changePwForm") ChangePasswordForm changePwForm, BindingResult bindingResult, @PathVariable(required = false) String username, Model model, Authentication authentication) {

    AppUser currentDetails = appUserRepository.findByUsernameIgnoreCase(authentication.getName());
    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

    if (!bc.matches(changePwForm.getOldPassword(), currentDetails.getPassword_hash())) {
      bindingResult.rejectValue("oldPassword", "err.oldPassword", "Inputted password does not match!");
    }

    if (!changePwForm.getNewPassword1().matches(changePwForm.getNewPassword2())) {
      bindingResult.rejectValue("newPassword2", "err.newPassword2", "New passwords don't match!");
    }

    if (bindingResult.hasErrors()) {
      model.addAttribute("changePwForm", changePwForm);
      model.addAttribute("user", appUserRepository.findByUsernameIgnoreCase(authentication.getName()));
      return "changepw";
    }

    currentDetails.setPassword_hash(bc.encode(changePwForm.getNewPassword1()));
    appUserRepository.save(currentDetails);

    model.addAttribute("message", "Password changed succesfully!");

    model.addAttribute("changePwForm", new ChangePasswordForm());
    model.addAttribute("user", currentDetails);

    return "changepw";

  }
  
  @RequestMapping(value="search", method=RequestMethod.GET)
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public String getSearch(Authentication authentication, Model model) {
    AppUser user = appUserRepository.findByUsernameIgnoreCase(authentication.getName());
    model.addAttribute("user", user);
    List<UsersBooks> usersBooks = usersBooksRepository.findByUser(user);
    model.addAttribute("usersBooks", usersBooks);

    List<AppUser> allUsers = (List<AppUser>) appUserRepository.findAll();
    List<AppUser> withoutLoggedInUser = allUsers.stream().filter(uzer -> uzer.getUsername() != user.getUsername()).collect(Collectors.toList());
    List<String> namesForSearch = withoutLoggedInUser.stream().map(uzer -> uzer.getUsername()).collect(Collectors.toList());
    model.addAttribute("usernamesForSearch", namesForSearch);
    return "search";
  }

  @RequestMapping(value="index", method=RequestMethod.GET)
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public String getIndex(Authentication authentication, Model model) {
    AppUser user = appUserRepository.findByUsernameIgnoreCase(authentication.getName());

    List<UsersBooks> books = (List<UsersBooks>) usersBooksRepository.findAll();
    List<UsersBooks> booksWithReviews = books.stream().filter(book -> book.getReview() != null).collect(Collectors.toList());

    Collections.reverse(booksWithReviews);

    List<AppUser> allUsers = (List<AppUser>) appUserRepository.findAll();
    List<AppUser> withoutLoggedInUser = allUsers.stream().filter(uzer -> uzer.getUsername() != user.getUsername()).collect(Collectors.toList());
    List<String> namesForSearch = withoutLoggedInUser.stream().map(uzer -> uzer.getUsername()).collect(Collectors.toList());
    model.addAttribute("usernamesForSearch", namesForSearch);
    model.addAttribute("namesLength", namesForSearch.size());

    model.addAttribute("booksWithReviews", booksWithReviews);
    model.addAttribute("user", user);
    return "index";
  }

  @RequestMapping(value="*", method=RequestMethod.GET)
  public String toIndex() {
    return "redirect:/index";
  }

}
