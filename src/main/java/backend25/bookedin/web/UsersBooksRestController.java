package backend25.bookedin.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import backend25.bookedin.model.AppUser;
import backend25.bookedin.model.AppUserRepository;
import backend25.bookedin.model.Book;
import backend25.bookedin.model.BookRepository;
import backend25.bookedin.model.PostModelUsersBooks;
import backend25.bookedin.model.PutModelUsersBooks;
import backend25.bookedin.model.Review;
import backend25.bookedin.model.ReviewRepository;
import backend25.bookedin.model.UsersBooks;
import backend25.bookedin.model.UsersBooksRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class UsersBooksRestController {

  @Autowired
  UsersBooksRepository usersBooksRepository;

  @Autowired
  AppUserRepository appUserRepository;

  @Autowired
  BookRepository bookRepository;

  @Autowired
  ReviewRepository reviewRepository;

  @PostMapping("api/usersbooks")
  public ResponseEntity<?> addUsersBook(@RequestBody PostModelUsersBooks newUsersBooks) {
    try {
      AppUser user = appUserRepository.findByUsernameIgnoreCase(newUsersBooks.getUsername());
      List<Book> book = bookRepository.findByIsbn(newUsersBooks.getIsbn());

      if (usersBooksRepository.findByBook(book.get(0)).size() != 0) {
        throw new Exception("This book already exists in the UsersBooks");
      }

      UsersBooks newUb = new UsersBooks(user, book.get(0), LocalDate.now());
      usersBooksRepository.save(newUb);

      return new ResponseEntity<UsersBooks>(newUb, HttpStatus.CREATED);

    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("api/usersbooks/{id}")
  public ResponseEntity<?> deleteUsersBook(@PathVariable(required = true) Long id) {
    try {
      UsersBooks toBeDeleted = usersBooksRepository.findById(id).orElse(null);
      usersBooksRepository.delete(toBeDeleted);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } catch(Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("api/usersbooks/{id}")
  public ResponseEntity<?> updateUsersBooks(@PathVariable (required = true) Long id, @RequestBody PutModelUsersBooks body) {
    try {
      UsersBooks toBeUpdated = usersBooksRepository.findById(id).orElse(null);
      if (toBeUpdated == null) {
        throw new Exception("No such id");
      }

      toBeUpdated.setReading_status(body.getReadingStatus());
      usersBooksRepository.save(toBeUpdated);

      return new ResponseEntity<UsersBooks>(toBeUpdated, HttpStatus.OK);

    } catch(Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
  

}
