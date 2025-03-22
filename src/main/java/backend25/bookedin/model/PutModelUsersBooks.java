package backend25.bookedin.model;

public class PutModelUsersBooks {

  String readingStatus;

  Long id;

  public String getReadingStatus() {
    return readingStatus;
  }

  public void setReadingStatus(String readingStatus) {
    this.readingStatus = readingStatus;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PutModelUsersBooks() {
  }

  public PutModelUsersBooks(String readingStatus, Long id) {
    this.readingStatus = readingStatus;
    this.id = id;
  }

  public PutModelUsersBooks(String readingStatus) {
    this.readingStatus = readingStatus;
  }

  @Override
  public String toString() {
    return "PutModelUsersBooks [readingStatus=" + readingStatus + ", id=" + id + "]";
  }

}
