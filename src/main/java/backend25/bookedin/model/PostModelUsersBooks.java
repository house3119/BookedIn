package backend25.bookedin.model;

public class PostModelUsersBooks {

  private String username, isbn;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public PostModelUsersBooks(String username, String isbn) {
    this.username = username;
    this.isbn = isbn;
  }

  public PostModelUsersBooks() {
  }

  @Override
  public String toString() {
    return "PostModelUsersBooks [username=" + username + ", isbn=" + isbn + "]";
  }

  

}
