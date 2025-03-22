package backend25.bookedin.web;

import org.springframework.web.bind.annotation.RestController;

import backend25.bookedin.model.AppUser;
import backend25.bookedin.model.Book;
import backend25.bookedin.model.PostModelReview;
import backend25.bookedin.model.PutModelReview;
import backend25.bookedin.model.Review;
import backend25.bookedin.model.ReviewRepository;
import backend25.bookedin.model.UsersBooks;
import backend25.bookedin.model.UsersBooksRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class ReviewRestController {

  @Autowired
  UsersBooksRepository usersBooksRepository;

  @Autowired
  ReviewRepository reviewRepository;

  @PostMapping("api/reviews")
  public ResponseEntity<?> addNewReview(@RequestBody PostModelReview review) {
    try {
      UsersBooks usersBooks = usersBooksRepository.findById(review.getUsersBooksId()).orElse(null);

      if (usersBooks == null) {
        throw new Exception("No such UsersBook found");
      }

      Review newReview = new Review(review.getReview(), review.getRating(), usersBooks);
      reviewRepository.save(newReview);

      usersBooks.setReview(newReview);
      usersBooksRepository.save(usersBooks);


      return new ResponseEntity<Review>(newReview, HttpStatus.CREATED);

    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("api/reviews/{id}")
  public ResponseEntity<?> updateReview(@PathVariable(required = true) Long id, @RequestBody PutModelReview updatedReview) {
    try {
      Review oldReview = reviewRepository.findById(id).orElse(null);

      if (oldReview == null) {
        throw new Exception("No such Review found");
      }

      oldReview.setRating(updatedReview.getRating());
      oldReview.setReview_text(updatedReview.getReview());
      reviewRepository.save(oldReview);

      return new ResponseEntity<Review>(oldReview, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
  

}
