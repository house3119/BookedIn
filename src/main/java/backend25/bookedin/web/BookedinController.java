package backend25.bookedin.web;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableMethodSecurity(securedEnabled = true)
public class BookedinController {

  @RequestMapping(value="/login")
  public String login() {	
    return "login";
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @RequestMapping(value="search", method=RequestMethod.GET)
  public String getSearch() {
    return "search";
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @RequestMapping(value="index", method=RequestMethod.GET)
  public String getIndex() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String name = auth.getName();
    Collection<? extends GrantedAuthority> role = auth.getAuthorities();
    System.out.println(name + " " + role);

    return "index";
  }


  @RequestMapping(value="*", method=RequestMethod.GET)
  public String toIndex() {
    return "redirect:/index";
  }

}
