package backend25.bookedin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import backend25.bookedin.model.AccountType;
import backend25.bookedin.model.AccountTypeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountTypeRepositoryTests {

  private AccountType testAccountType;

  @Autowired
  private AccountTypeRepository accountTypeRepository;

  @BeforeEach
  public void initAccountTypeTest() {
    this.testAccountType = new AccountType("TEST");
    accountTypeRepository.save(testAccountType);
  }

  @Test
  public void addingNewAccountTypeWorks() {
    assertThat(accountTypeRepository.findById(this.testAccountType.getAccount_type_id()).orElse(null)).isNotNull();
  }

  @Test
  public void searchAccountTypeByTypeWorks() {
    AccountType result = accountTypeRepository.findByType("TEST");
    assertThat(result).isNotNull();
  }

  @Test
  public void deleteAccountTypeWorks() {
    Long id = testAccountType.getAccount_type_id();
    accountTypeRepository.delete(this.testAccountType);
    assertThat(accountTypeRepository.findById(id).orElse(null)).isNull();
  }

}
