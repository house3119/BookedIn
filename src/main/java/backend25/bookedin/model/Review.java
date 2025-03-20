package backend25.bookedin.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Reviews")
public class Review {

  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id", nullable = false, updatable = false)
	private Long review_id;

  @NotNull(message = "Date is required")
  @Column(name = "review_added", nullable = false, updatable = false)
  private LocalDate reviewAdded;

  @NotBlank(message = "Review text is required")
  @Size(max = 3000, message = "Max 3000 characters")
  @Column(name = "review_text", nullable = false, updatable = false)
  private String review_text;

  @NotNull(message = "Rating is required")
  @Min(1)
  @Max(5)
  @Column(name = "rating", nullable = false, updatable = false)
  private int rating;

  @NotNull(message = "Required")
  @OneToOne
  @JoinColumn(name = "usersBooks_id")
  private UsersBooks users_books;

  public Review() {
  }

  public Review(
      @NotBlank(message = "Review text is required") @Size(max = 3000, message = "Max 3000 characters") String review_text,
      @NotNull(message = "Rating is required") @Min(0) @Max(5) int rating,
      @NotEmpty(message = "Required") UsersBooks users_books) {
    this.review_text = review_text;
    this.rating = rating;
    this.users_books = users_books;
    this.reviewAdded = LocalDate.now();
  }

  public Long getReview_id() {
    return review_id;
  }

  public void setReview_id(Long review_id) {
    this.review_id = review_id;
  }

  public LocalDate getReviewAdded() {
    return reviewAdded;
  }

  public void setReviewAdded(LocalDate reviewAdded) {
    this.reviewAdded = reviewAdded;
  }

  public String getReview_text() {
    return review_text;
  }

  public void setReview_text(String review_text) {
    this.review_text = review_text;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public UsersBooks getUsers_books() {
    return users_books;
  }

  public void setUsers_books(UsersBooks users_books) {
    this.users_books = users_books;
  }

  @Override
  public String toString() {
    return "Review [review_id=" + review_id + ", reviewAdded=" + reviewAdded + ", review_text=" + review_text
        + ", rating=" + rating + ", users_books=" + users_books + "]";
  }

}
