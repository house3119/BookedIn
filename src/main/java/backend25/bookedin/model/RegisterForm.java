package backend25.bookedin.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterForm {

  @NotEmpty(message = "Username is required")
  @Size(min= 5 , max = 100)
  String username;

  @NotEmpty(message = "Password is required")
  @Size(min= 5 , max = 100)
  String password1;

  @NotEmpty(message = "Password confirmation is required")
  @Size(min= 5 , max = 100)
  String password2;

  public RegisterForm() {
  }

  public RegisterForm(String username, String password1, String password2) {
    this.username = username;
    this.password1 = password1;
    this.password2 = password2;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword1() {
    return password1;
  }

  public void setPassword1(String password1) {
    this.password1 = password1;
  }

  public String getPassword2() {
    return password2;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  @Override
  public String toString() {
    return "RegisterForm [username=" + username + ", password1=" + password1 + ", password2=" + password2 + "]";
  }

}
