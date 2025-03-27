package backend25.bookedin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import backend25.bookedin.model.AccountType;
import backend25.bookedin.model.AccountTypeRepository;
import backend25.bookedin.model.AppUser;
import backend25.bookedin.model.AppUserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppUserRepositoryTests {

  private AppUser testUser;
  private AccountType testType;

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private AccountTypeRepository accountTypeRepository;

  @BeforeEach
  public void initTestUser() {
    this.testType = new AccountType("TEST");
    accountTypeRepository.save(testType);

    this.testUser = new AppUser("testUser", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", testType);
    appUserRepository.save(testUser);
  }

  @Test
  public void addingNewUserWorks() {
    assertThat(testUser.getUser_id()).isNotNull();
  }

  @Test
  public void searchUserIdWorks() {
    Long id = testUser.getUser_id();
    AppUser user = appUserRepository.findById(id).orElse(null);
    assertThat(user.getUser_id()).isNotNull();
  }

  @Test
  public void searchUserByUsernameWorks() {
    AppUser user = appUserRepository.findByUsernameIgnoreCase("testUser");
    assertThat(user.getUser_id()).isNotNull();
  }

  @Test
  public void searchUserByUsernameIgnoresCase() {
    AppUser user = appUserRepository.findByUsernameIgnoreCase("TESTUSER");
    assertThat(user.getUser_id()).isNotNull();
  }

  @Test
  public void deleteUserWorks() {
    appUserRepository.delete(testUser);
    AppUser user = appUserRepository.findByUsernameIgnoreCase("testUser");
    assertThat(user).isNull();
  }

}
