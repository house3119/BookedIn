package backend25.bookedin.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Reviews")
public class Review {

  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id", nullable = false, updatable = false)
	private Long review_id;

  @NotNull(message = "User is required")
  @ManyToOne
  @JoinColumn(name = "user_id")
  private AppUser user;

  @NotNull(message = "Book is required")
  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  @NotNull(message = "Date is required")
  @Column(name = "review_added", nullable = false, updatable = false)
  private LocalDate reviewAdded;

  @NotBlank(message = "Review text is required")
  @Size(max = 3000, message = "Max 3000 characters")
  @Column(name = "review_text", nullable = false, updatable = false)
  private String review_text;

  @NotNull(message = "Rating is required")
  @Min(0)
  @Max(5)
  @Column(name = "rating", nullable = false, updatable = false)
  private int rating;

  public Review() {
  }

  public Review(@NotNull(message = "User is required") AppUser user, @NotNull(message = "Book is required") Book book,
      @NotNull(message = "Date is required") LocalDate reviewAdded,
      @NotBlank(message = "Review text is required") @Size(max = 3000, message = "Max 3000 characters") String review_text,
      @NotNull(message = "Rating is required") @Min(0) @Max(5) int rating) {
    this.user = user;
    this.book = book;
    this.reviewAdded = reviewAdded;
    this.review_text = review_text;
    this.rating = rating;
  }

  public Long getReview_id() {
    return review_id;
  }

  public void setReview_id(Long review_id) {
    this.review_id = review_id;
  }

  public AppUser getUser() {
    return user;
  }

  public void setUser(AppUser user) {
    this.user = user;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
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

  @Override
  public String toString() {
    return "Review [review_id=" + review_id + ", user=" + user + ", book=" + book + ", reviewAdded=" + reviewAdded
        + ", review_text=" + review_text + ", rating=" + rating + "]";
  }

}
