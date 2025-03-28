package backend25.bookedin.model;

import org.springframework.data.repository.CrudRepository;


public interface CountryRepository extends CrudRepository<Country, Long> {
  Country findByName(String name);
}
