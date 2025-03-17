package backend25.bookedin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend25.bookedin.model.AccountType;
import backend25.bookedin.model.AccountTypeRepository;
import backend25.bookedin.model.Country;
import backend25.bookedin.model.CountryRepository;

@SpringBootApplication
public class BookedinApplication {

    private final AccountTypeRepository accountTypeRepository;

    private final CountryRepository countryRepository;

	private static final Logger log = LoggerFactory.getLogger(BookedinApplication.class);

    BookedinApplication(CountryRepository countryRepository, AccountTypeRepository accountTypeRepository) {
        this.countryRepository = countryRepository;
        this.accountTypeRepository = accountTypeRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(BookedinApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookedinRunner (
		AccountTypeRepository accountTypeRepository,
		CountryRepository countryRepository
	) {
		return(args) -> {

			log.info("Add account types to db...");
			accountTypeRepository.save(new AccountType("USER", "Basic user without any extra priviledges."));
			accountTypeRepository.save(new AccountType("ADMIN", "Admin level user. Can perform various actions that normal users can't."));

			log.info("Add some countries to db...");
			countryRepository.save(new Country("Finland"));
			countryRepository.save(new Country("Sweden"));
			countryRepository.save(new Country("Denmark"));

		};
	}

}
