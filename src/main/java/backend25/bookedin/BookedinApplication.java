package backend25.bookedin;

import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.opencsv.CSVReader;

import backend25.bookedin.model.AccountType;
import backend25.bookedin.model.AccountTypeRepository;
import backend25.bookedin.model.AppUser;
import backend25.bookedin.model.AppUserRepository;
import backend25.bookedin.model.Country;
import backend25.bookedin.model.CountryRepository;

@SpringBootApplication
public class BookedinApplication {

	private static final Logger log = LoggerFactory.getLogger(BookedinApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookedinApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookedinRunner (
		AccountTypeRepository accountTypeRepository,
		AppUserRepository appUserRepository,
		CountryRepository countryRepository
	) {
		return(args) -> {

			log.info("Add account types to db...");
			accountTypeRepository.save(new AccountType("USER", "Basic user without any extra priviledges."));
			accountTypeRepository.save(new AccountType("ADMIN", "Admin level user. Can perform various actions that normal users can't."));

			log.info("Add countries to db from file...");
			try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/static/FCDO_Geographical_Names_Index_March_2024.csv"))) {
				String[] next;
				while ((next = csvReader.readNext()) != null) {
					if (next[1] != "Name") {
						countryRepository.save(new Country(next[1]));
					}
				}
			}

			log.info("Add some users to db...");
			AppUser user1 = new AppUser(
				"user",
				"https://www.detectiveconanworld.com/wiki/images/thumb/d/db/Kogoro%27s_diff_look.jpg/197px-Kogoro%27s_diff_look.jpg", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6",
				countryRepository.findByName("Finland"),
				1969,
				accountTypeRepository.findByType("USER")
			);
			AppUser user2 = new AppUser(
				"admin",
				"https://www.giantbomb.com/a/uploads/scale_small/7/77081/1249176-conan_edogawa.jpg",
				"$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C",
				countryRepository.findByName("Finland"),
				1988,
				accountTypeRepository.findByType("ADMIN")
			);

			appUserRepository.save(user1);
			appUserRepository.save(user2);


		};

	}

}
