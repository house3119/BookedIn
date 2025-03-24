package backend25.bookedin.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ChangePasswordForm {

  @NotEmpty(message = "Old password is required")
  @Size(min= 1 , max = 100, message = "Length needs to be over 5 characters (max 100 characters).")
  String oldPassword;

  @NotEmpty(message = "New password is required")
  @Size(min= 5 , max = 100, message = "Length needs to be over 5 characters (max 100 characters).")
  String newPassword1;

  @NotEmpty(message = "New password confirmation is required")
  @Size(min= 5 , max = 100, message = "Length needs to be over 5 characters (max 100 characters).")
  String newPassword2;

  public ChangePasswordForm() {
  }

  public ChangePasswordForm(
      @NotEmpty(message = "Old password is required") @Size(min = 1, max = 100, message = "Length needs to be over 5 characters (max 100 characters).") String oldPassword,
      @NotEmpty(message = "New password is required") @Size(min = 5, max = 100, message = "Length needs to be over 5 characters (max 100 characters).") String newPassword1,
      @NotEmpty(message = "New password confirmation is required") @Size(min = 5, max = 100, message = "Length needs to be over 5 characters (max 100 characters).") String newPassword2) {
    this.oldPassword = oldPassword;
    this.newPassword1 = newPassword1;
    this.newPassword2 = newPassword2;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword1() {
    return newPassword1;
  }

  public void setNewPassword1(String newPassword1) {
    this.newPassword1 = newPassword1;
  }

  public String getNewPassword2() {
    return newPassword2;
  }

  public void setNewPassword2(String newPassword2) {
    this.newPassword2 = newPassword2;
  }

  @Override
  public String toString() {
    return "ChangePasswordForm [oldPassword=" + oldPassword + ", newPassword1=" + newPassword1 + ", newPassword2="
        + newPassword2 + "]";
  }

}
