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
import backend25.bookedin.model.UsersBooks;
import backend25.bookedin.model.UsersBooksRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UsersBooksRestController {

  @Autowired
  UsersBooksRepository usersBooksRepository;

  @Autowired
  AppUserRepository appUserRepository;

  @Autowired
  BookRepository bookRepository;

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
  

}
