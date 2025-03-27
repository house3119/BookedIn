package backend25.bookedin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import backend25.bookedin.model.Book;
import backend25.bookedin.model.BookRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTests {

  private Book testBook;

  @Autowired
  private BookRepository bookRepository;

  @BeforeEach
  public void initBookTest() {
    this.testBook = new Book("testTitle", "testAuthor", "2000", "0123456789", 99, "Kieli");
    bookRepository.save(testBook);
  }

  @Test
  public void addingNewBookWorks() {
    assertThat(testBook.getBook_id()).isNotNull();
  }

  @Test
  public void searchBookByIsbnWorks() {
    List<Book> results = bookRepository.findByIsbn("0123456789");
    assertThat(results).hasSize(1);
  }

  @Test
  public void searchBookByIdWorks() {
    Long id = testBook.getBook_id();
    Book result = bookRepository.findById(id).orElse(null);
    assertThat(result).isNotNull();
  }

  @Test
  public void deleteBookWorks() {
    Long id = testBook.getBook_id();
    bookRepository.delete(testBook);
    Book result = bookRepository.findById(id).orElse(null);
    assertThat(result).isNull();
  }

}
