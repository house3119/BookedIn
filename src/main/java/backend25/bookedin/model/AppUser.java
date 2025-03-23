package backend25.bookedin.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Users")
public class AppUser {

  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, updatable = false)
	private Long user_id;

  @NotEmpty(message = "Username is mandatory")
  @Size(max = 100, message = "Username can be max 100 characters long")
  @Column(name = "username", nullable = false, updatable = false, unique = true)
  private String username;

  @Size(max = 200, message = "Avatar URL can be max 200 characters long")
  @Column(name = "avatar_url", nullable = true, updatable = true)
  private String avatar_url;

  @NotEmpty(message = "Password hash is mandatory")
  @Size(max = 60)
  @Column(name = "password_hash", nullable = false)
  private String password_hash;

  @ManyToOne
  @JoinColumn(name = "country_id", nullable = true, updatable = true)
  private Country country;

  @Column(name = "birth_year", nullable = true, updatable = true)
  private int age;

  @ManyToOne
  @JoinColumn(name = "account_type_id")
  private AccountType account_type;

  @NotNull(message = "Date joined is required")
  @Column(name = "date_joined", nullable = false, updatable = false)
  private LocalDate date_joined;

  @ManyToMany
  @JsonIgnore
  Set<AppUser> followers;

  @ManyToMany
  @JsonIgnore
  Set<AppUser> following;

  public LocalDate getDate_joined() {
    return date_joined;
  }

  public void setDate_joined(LocalDate date_joined) {
    this.date_joined = date_joined;
  }

  public Set<AppUser> getFollowers() {
    return followers;
  }

  public void setFollowers(Set<AppUser> followers) {
    this.followers = followers;
  }

  public Set<AppUser> getFollowing() {
    return following;
  }

  public void setFollowing(Set<AppUser> following) {
    this.following = following;
  }

  public AppUser() { }




  public AppUser(
      @NotEmpty(message = "Username is mandatory") @Size(max = 100, message = "Username can be max 100 characters long") String username,
      @NotEmpty(message = "Password hash is mandatory") @Size(max = 60) String password_hash,
      AccountType account_type) {
    this.username = username;
    this.password_hash = password_hash;
    this.account_type = account_type;
    this.followers = new HashSet<AppUser>();
    this.following = new HashSet<AppUser>();
    this.avatar_url = "";
    this.date_joined = LocalDate.now();
  }

  public AppUser(
      @NotEmpty(message = "Username is mandatory") @Size(max = 100, message = "Username can be max 100 characters long") String username,
      @Size(max = 200, message = "Avatar URL can be max 200 characters long") String avatar_url,
      @NotEmpty(message = "Password hash is mandatory") @Size(max = 60) String password_hash, Country country, int age,
      AccountType account_type) {
    this.username = username;
    this.avatar_url = avatar_url;
    this.password_hash = password_hash;
    this.country = country;
    this.age = age;
    this.account_type = account_type;
    this.date_joined = LocalDate.now();
  }

  public AppUser(
      @NotEmpty(message = "Username is mandatory") @Size(max = 100, message = "Username can be max 100 characters long") String username,
      @Size(max = 200, message = "Avatar URL can be max 200 characters long") String avatar_url,
      @NotEmpty(message = "Password hash is mandatory") @Size(max = 60) String password_hash, Country country, int age,
      AccountType account_type, Set<AppUser> followers, Set<AppUser> following) {
    this.username = username;
    this.avatar_url = avatar_url;
    this.password_hash = password_hash;
    this.country = country;
    this.age = age;
    this.account_type = account_type;
    this.followers = followers;
    this.following = following;
    this.date_joined = LocalDate.now();
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAvatar_url() {
    return avatar_url;
  }

  public void setAvatar_url(String avatar_url) {
    this.avatar_url = avatar_url;
  }

  public String getPassword_hash() {
    return password_hash;
  }

  public void setPassword_hash(String password_hash) {
    this.password_hash = password_hash;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public AccountType getAccount_type() {
    return account_type;
  }

  public void setAccount_type(AccountType account_type) {
    this.account_type = account_type;
  }

  @Override
  public String toString() {
    return "AppUser [user_id=" + user_id + ", username=" + username + ", avatar_url=" + avatar_url + ", password_hash="
        + password_hash + ", country=" + country + ", age=" + age + ", account_type=" + account_type + ", followers="
        + followers + ", following=" + following + "]";
  }

}
