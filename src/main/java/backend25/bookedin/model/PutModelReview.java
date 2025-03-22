package backend25.bookedin.model;

public class PutModelReview {

  String review;

  int rating;

  public PutModelReview() {
  }

  public PutModelReview(String review, int rating) {
    this.review = review;
    this.rating = rating;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  @Override
  public String toString() {
    return "PutModelReview [review=" + review + ", rating=" + rating + "]";
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

}
