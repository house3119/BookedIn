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


		};

	}

}
