package backend25.bookedin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Account_types")
public class AccountType {

  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_type_id", nullable = false, updatable = false)
	private Long account_type_id;

  @NotEmpty(message = "Account type is mandatory")
  @Size(max = 100)
  @Column(name = "account_type", nullable = false, updatable = false)
  private String type;

  @Size(max = 200)
  @Column(name = "description", nullable = false, updatable = false)
  private String description;

  public AccountType() { }

  public AccountType(@NotEmpty(message = "Account type is mandatory") @Size(max = 100) String account_type) {
    this.type = account_type;
  }

  public AccountType(@NotEmpty(message = "Account type is mandatory") @Size(max = 100) String account_type,
      @Size(max = 200) String description) {
    this.type = account_type;
    this.description = description;
  }

  public Long getAccount_type_id() {
    return account_type_id;
  }

  public void setAccount_type_id(Long account_type_id) {
    this.account_type_id = account_type_id;
  }

  public String getAccount_type() {
    return type;
  }

  public void setAccount_type(String account_type) {
    this.type = account_type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "AccountType [account_type_id=" + account_type_id + ", account_type=" + type + ", description="
        + description + "]";
  }

}
