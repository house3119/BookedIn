package backend25.bookedin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import backend25.bookedin.model.Book;
import backend25.bookedin.model.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@EnableMethodSecurity(securedEnabled = true)
public class BookRestController {

  @Autowired
  private BookRepository bookRepository;

  @GetMapping("/api/books")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public ResponseEntity<List<Book>> getAllBooks () {
    System.out.println("okokokokook");
    try {
      List<Book> books = (List<Book>) bookRepository.findAll();
      if (books.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(books);
    } catch(Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching books", e);
    }
  }

  @PostMapping("/api/books")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public ResponseEntity<?> AddBook (@RequestBody Book newBook) {
      try {
        List<Book> alreadyExists = bookRepository.findByIsbn(newBook.getIsbn());

        if (alreadyExists.size() != 0) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book already exists in the db");
        } else {
          bookRepository.save(newBook);
          return new ResponseEntity<Book>(newBook, HttpStatus.CREATED);
        }

      } catch (Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
  }
  
}
