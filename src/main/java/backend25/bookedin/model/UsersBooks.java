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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "UsersBooks")
public class UsersBooks {

  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "row_id", nullable = false, updatable = false)
	private Long usersBooks_id;

  @NotNull(message = "User is required")
  @ManyToOne
  @JoinColumn(name = "user_id")
  private AppUser user;

  @NotNull(message = "Book is required")
  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  @NotNull(message = "Date is required")
  @Column(name = "date_added", nullable = false, updatable = false)
  private LocalDate dateAdded;

  @NotNull(message = "Required")
  @Column(name = "reading_status")
  private String reading_status;

  @OneToOne
  @JoinColumn(name = "review_id")
  private Review review;

  public UsersBooks() {
  }

  public String getReading_status() {
    return reading_status;
  }

  public void setReading_status(String reading_status) {
    this.reading_status = reading_status;
  }

  public Review getReview() {
    return review;
  }

  public void setReview(Review review) {
    this.review = review;
  }

  public UsersBooks(@NotNull(message = "User is required") AppUser user,
      @NotNull(message = "Book is required") Book book, @NotNull(message = "Date is required") LocalDate dateAdded) {
    this.user = user;
    this.book = book;
    this.dateAdded = dateAdded;
    this.reading_status = "No status";
  }

  public UsersBooks(@NotNull(message = "User is required") AppUser user,
      @NotNull(message = "Book is required") Book book, @NotNull(message = "Date is required") LocalDate dateAdded,
      @NotNull(message = "Required") String reading_status) {
    this.user = user;
    this.book = book;
    this.dateAdded = dateAdded;
    this.reading_status = reading_status;
  }

  public Long getUsersBooks_id() {
    return usersBooks_id;
  }

  public void setUsersBooks_id(Long usersBooks_id) {
    this.usersBooks_id = usersBooks_id;
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

  public LocalDate getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(LocalDate dateAdded) {
    this.dateAdded = dateAdded;
  }

  @Override
  public String toString() {
    return "UsersBooks [usersBooks_id=" + usersBooks_id + ", user=" + user + ", book=" + book + ", dateAdded="
        + dateAdded + "]";
  }

}
