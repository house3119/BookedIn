package backend25.bookedin;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import backend25.bookedin.model.AccountType;
import backend25.bookedin.model.AccountTypeRepository;
import backend25.bookedin.model.AppUser;
import backend25.bookedin.model.AppUserRepository;
import backend25.bookedin.model.Book;
import backend25.bookedin.model.BookRepository;
import backend25.bookedin.model.UsersBooks;
import backend25.bookedin.model.UsersBooksRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersBooksRepositoryTests {

  private UsersBooks testUsersBooks;
  private AppUser testUser;
  private Book testBook;
  private AccountType testType;

  @Autowired
  private UsersBooksRepository usersBooksRepository;

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AccountTypeRepository accountTypeRepository;

  @BeforeEach
  public void initUsersBooksTest() {
    this.testType = new AccountType("TEST");
    accountTypeRepository.save(testType);

    this.testUser = new AppUser("TestUser", "testHash", testType);
    appUserRepository.save(testUser);

    this.testBook = new Book("testTitle", "testAuthor", "2000", "0123456789", 99, "Kieli");
    bookRepository.save(testBook);

    this.testUsersBooks = new UsersBooks(testUser, testBook, LocalDate.now());
    usersBooksRepository.save(testUsersBooks);
  }

  @Test
  public void addingNewUsersBooksWorks() {
    assertThat(usersBooksRepository.findById(this.testUsersBooks.getUsersBooks_id()).orElse(null)).isNotNull();
  }

  @Test
  public void searchUsersBooksByUserWorks() {
    assertThat(usersBooksRepository.findByUser(this.testUser)).hasSize(1);

    Book testBook2 = new Book("testTitle2", "testAuthor2", "2002", "01234567890", 199, "Kieli2");
    bookRepository.save(testBook2);

    usersBooksRepository.save(new UsersBooks(testUser, testBook2, LocalDate.now()));

    assertThat(usersBooksRepository.findByUser(this.testUser)).hasSize(2);
  }

  @Test
  public void searchUsersBooksByBookWorks () {
    assertThat(usersBooksRepository.findByBook(this.testBook)).hasSize(1);

    AppUser testUser2 = new AppUser("TestUser2", "testHash2", testType);
    appUserRepository.save(testUser2);

    usersBooksRepository.save(new UsersBooks(testUser2, this.testBook, LocalDate.now()));

    assertThat(usersBooksRepository.findByBook(this.testBook)).hasSize(2);
  }

  @Test
  public void deleteUsersBooksWorks() {
    Long id = this.testUsersBooks.getUsersBooks_id();
    usersBooksRepository.delete(this.testUsersBooks);
    assertThat(usersBooksRepository.findById(id).orElse(null)).isNull();
  }

}
