package backend25.bookedin.model;

public class PostModelReview {

  private String review;

  private int rating;

  private Long usersBooksId;

  public PostModelReview() {
  }

  public PostModelReview(String review, int rating) {
    this.review = review;
    this.rating = rating;
  }

  public PostModelReview(String review, int rating, Long usersBooksId) {
    this.review = review;
    this.rating = rating;
    this.usersBooksId = usersBooksId;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public Long getUsersBooksId() {
    return usersBooksId;
  }

  public void setUsersBooksId(Long usersBooksId) {
    this.usersBooksId = usersBooksId;
  }

  @Override
  public String toString() {
    return "PostModelReview [review=" + review + ", rating=" + rating + ", usersBooksId=" + usersBooksId + "]";
  }

}
