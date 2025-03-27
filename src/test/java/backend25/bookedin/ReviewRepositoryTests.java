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
import backend25.bookedin.model.Review;
import backend25.bookedin.model.ReviewRepository;
import backend25.bookedin.model.UsersBooks;
import backend25.bookedin.model.UsersBooksRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTests {

  private Review testReview;
  private UsersBooks testUsersBooks;
  private AppUser testUser;
  private Book testBook;
  private AccountType testType;

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private UsersBooksRepository usersBooksRepository;

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AccountTypeRepository accountTypeRepository;

  @BeforeEach
  public void initReviewTest() {
    this.testType = new AccountType("TEST");
    accountTypeRepository.save(testType);

    this.testUser = new AppUser("TestUser", "testHash", testType);
    appUserRepository.save(testUser);

    this.testBook = new Book("testTitle", "testAuthor", "2000", "0123456789", 99, "Kieli");
    bookRepository.save(testBook);

    this.testUsersBooks = new UsersBooks(testUser, testBook, LocalDate.now());
    usersBooksRepository.save(testUsersBooks);

    this.testReview = new Review("Review test", 5, testUsersBooks);
    reviewRepository.save(testReview);
  }

  @Test
  public void addingNewReviewWorks() {
    assertThat(reviewRepository.findById(testReview.getReview_id())).isNotNull();
  }

  @Test
  public void searchReviewByUsersBooksWorks() {
    assertThat(reviewRepository.findByUsersBooks(this.testUsersBooks)).hasSize(1);
  }

  @Test
  public void updateReviewWorks() {
    Review toBeUpdated = reviewRepository.findById(this.testReview.getReview_id()).orElse(null);
    assertThat(toBeUpdated.getRating()).isEqualTo(5);

    toBeUpdated.setRating(1);
    reviewRepository.save(toBeUpdated);

    assertThat(reviewRepository.findById(this.testReview.getReview_id()).orElse(null).getRating()).isEqualTo(1);
  }

  @Test
  public void deleteReviewWorks() {
    Long id = this.testReview.getReview_id();
    reviewRepository.delete(this.testReview);
    assertThat(reviewRepository.findById(id).orElse(null)).isNull();
  }

}
