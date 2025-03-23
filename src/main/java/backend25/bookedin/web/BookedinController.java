package backend25.bookedin.web;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import backend25.bookedin.model.AppUser;
import backend25.bookedin.model.AppUserRepository;
import backend25.bookedin.model.Book;
import backend25.bookedin.model.CountryRepository;
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

  public BookedinController(AppUserRepository appUserRepository, UsersBooksRepository usersBooksRepository, ReviewRepository reviewRepository, CountryRepository countryRepository) {
    this.appUserRepository = appUserRepository;
    this.usersBooksRepository = usersBooksRepository;
    this.reviewRepository = reviewRepository;
    this.countryRepository = countryRepository;
  }

  @RequestMapping(value="/login")
  public String login() {	
    return "login";
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @RequestMapping(value="users/{username}", method=RequestMethod.GET)
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
      return "profile";
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @RequestMapping(value="users/{username}/edit", method=RequestMethod.GET)
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

    return "editprofile";

  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @RequestMapping(value="users/{username}", method=RequestMethod.POST)
  public String saveEditProfile(@Valid AppUser editedUser, BindingResult bindingResult, @PathVariable(required = false) String username, Model model, Authentication authentication) {
    AppUser oldUser = appUserRepository.findByUsernameIgnoreCase(username);
    oldUser.setAvatar_url(editedUser.getAvatar_url());
    oldUser.setAge(editedUser.getAge());
    oldUser.setCountry(editedUser.getCountry());
    System.out.println("COUNTRY:    " + editedUser.getCountry());
    appUserRepository.save(oldUser);

    return "redirect:/users/bilbo";
  }
  

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @RequestMapping(value="search", method=RequestMethod.GET)
  public String getSearch(Authentication authentication, Model model) {
    AppUser user = appUserRepository.findByUsernameIgnoreCase(authentication.getName());
    model.addAttribute("user", user);
    List<UsersBooks> usersBooks = usersBooksRepository.findByUser(user);
    model.addAttribute("usersBooks", usersBooks);
    return "search";
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @RequestMapping(value="index", method=RequestMethod.GET)
  public String getIndex(Authentication authentication, Model model) {
    AppUser user = appUserRepository.findByUsernameIgnoreCase(authentication.getName());
    model.addAttribute("user", user);
    return "index";
  }


  @RequestMapping(value="*", method=RequestMethod.GET)
  public String toIndex() {
    return "redirect:/index";
  }

}
