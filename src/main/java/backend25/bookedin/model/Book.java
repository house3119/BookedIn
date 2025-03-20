package backend25.bookedin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Books")
public class Book {

  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id", nullable = false, updatable = false)
	private Long book_id;

  @NotEmpty(message = "Title is required")
  @Size(max = 100, message = "Max 100 characters")
  @Column(name = "title", nullable = false, unique = true)
  private String title;

  @NotEmpty(message = "Author is required")
  @Size(max = 100, message = "Max 100 characters")
  @Column(name = "author", nullable = false)
  private String author;

  @NotNull(message = "Published year is required")
  @Column(name = "published", nullable = false)
  private int published_year;

  @Size(max = 3000, message = "Max 3000 characters")
  @Column(name = "description", nullable = false)
  private String description;

  @NotEmpty(message = "ISBN is required")
  @Size(max = 18, message = "Max 18 characters")
  @Column(name = "isbn", nullable = false, unique = true)
  private String isbn;

  @NotNull(message = "Page count is required")
  @Min(1)
  @Column(name = "page_count", nullable = false)
  private int page_count;

  @NotEmpty(message = "Language is required")
  @Column(name = "language", nullable = false)
  private String language;

  @Size(max = 200, message = "Max 200 characters")
  @Column(name = "img_link", nullable = false)
  private String img_link;

  public Book() {
  }

  public Book(@NotEmpty(message = "Title is required") @Size(max = 100, message = "Max 100 characters") String title,
      @NotEmpty(message = "Author is required") @Size(max = 100, message = "Max 100 characters") String author,
      @NotNull(message = "Published year is required") int published_year,
      @NotEmpty(message = "ISBN is required") @Size(max = 13, message = "Max 13 characters") String isbn,
      @NotNull(message = "Page count is required") @Min(1) int page_count,
      @NotEmpty(message = "Language is required") String language) {
    this.title = title;
    this.author = author;
    this.published_year = published_year;
    this.isbn = isbn;
    this.page_count = page_count;
    this.language = language;
  }

  public Book(@NotEmpty(message = "Title is required") @Size(max = 100, message = "Max 100 characters") String title,
      @NotEmpty(message = "Author is required") @Size(max = 100, message = "Max 100 characters") String author,
      @NotNull(message = "Published year is required") int published_year,
      @Size(max = 3000, message = "Max 3000 characters") String description,
      @NotEmpty(message = "ISBN is required") @Size(max = 13, message = "Max 13 characters") String isbn,
      @NotNull(message = "Page count is required") @Min(1) int page_count,
      @NotEmpty(message = "Language is required") String language,
      @Size(max = 200, message = "Max 200 characters") String img_link) {
    this.title = title;
    this.author = author;
    this.published_year = published_year;
    this.description = description;
    this.isbn = isbn;
    this.page_count = page_count;
    this.language = language;
    this.img_link = img_link;
  }

  public Long getBook_id() {
    return book_id;
  }

  public void setBook_id(Long book_id) {
    this.book_id = book_id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getPublished_year() {
    return published_year;
  }

  public void setPublished_year(int published_year) {
    this.published_year = published_year;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public int getPage_count() {
    return page_count;
  }

  public void setPage_count(int page_count) {
    this.page_count = page_count;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getImg_link() {
    return img_link;
  }

  public void setImg_link(String img_link) {
    this.img_link = img_link;
  }

  @Override
  public String toString() {
    return "Book [book_id=" + book_id + ", title=" + title + ", author=" + author + ", published_year=" + published_year
        + ", description=" + description + ", isbn=" + isbn + ", page_count=" + page_count + ", language=" + language
        + ", img_link=" + img_link + "]";
  }

}

