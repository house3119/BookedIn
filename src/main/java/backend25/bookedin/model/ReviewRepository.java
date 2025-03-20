package backend25.bookedin.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface ReviewRepository extends CrudRepository<Review, Long> {
  
}
