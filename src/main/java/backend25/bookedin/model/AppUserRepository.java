package backend25.bookedin.model;

import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
  AppUser findByUsernameIgnoreCase(String username);
}
