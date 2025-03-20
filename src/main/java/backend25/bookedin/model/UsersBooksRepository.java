package backend25.bookedin.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface UsersBooksRepository extends CrudRepository<UsersBooks, Long> {
  List<UsersBooks> findByUser(AppUser user);
}
