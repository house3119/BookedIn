package backend25.bookedin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import backend25.bookedin.model.Country;
import backend25.bookedin.model.CountryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CountryRepositoryTests {

  private Country testCountry;

  @Autowired
  private CountryRepository countryRepository;

  @BeforeEach
  public void initCountryTest() {
    this.testCountry = new Country("TestCountry");
    countryRepository.save(testCountry);
  }

  @Test
  public void addingCountryWorks() {
    assertThat(countryRepository.findById(testCountry.getCountry_id())).isNotNull();
  }

  @Test
  public void searchCountryByNameWorks() {
    assertThat(countryRepository.findByName("TestCountry")).isNotNull();
  }

  @Test
  public void deleteCountryWorks() {
    Long id = testCountry.getCountry_id();
    countryRepository.delete(testCountry);
    assertThat(countryRepository.findById(id).orElse(null)).isNull();
  }

}
