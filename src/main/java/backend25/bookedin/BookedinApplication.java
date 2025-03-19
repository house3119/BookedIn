package backend25.bookedin;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.HashSet;

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
import backend25.bookedin.model.Book;
import backend25.bookedin.model.BookRepository;
import backend25.bookedin.model.Country;
import backend25.bookedin.model.CountryRepository;
import backend25.bookedin.model.Review;
import backend25.bookedin.model.ReviewRepository;
import backend25.bookedin.model.UsersBooks;
import backend25.bookedin.model.UsersBooksRepository;

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
		BookRepository bookRepository,
		CountryRepository countryRepository,
		UsersBooksRepository usersBooksRepository,
		ReviewRepository reviewRepository
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
				"Bilbo",
				"https://static.wikia.nocookie.net/peter-jacksons-the-lord-of-the-rings-trilogy/images/f/ff/Hudfzq7jltpvuhft.jpg/revision/latest?cb=20160618134352", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6",
				countryRepository.findByName("Sweden"),
				1969,
				accountTypeRepository.findByType("USER"),
				new HashSet<AppUser>(),
				new HashSet<AppUser>()
			);
			AppUser user2 = new AppUser(
				"Frodo",
				"https://static.wikia.nocookie.net/pjmidearthfilms/images/3/34/Frodo.jpg/revision/latest?cb=20200725035857",
				"$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C",
				countryRepository.findByName("Finland"),
				1988,
				accountTypeRepository.findByType("ADMIN"),
				new HashSet<AppUser>(),
				new HashSet<AppUser>()
			);
			AppUser user3 = new AppUser(
				"Gandalf",
				"https://static.wixstatic.com/media/065e6b_dd1a8624bd5b40c9848aeec671ed811b.png/v1/fill/w_568,h_444,al_c,q_85,usm_0.66_1.00_0.01,enc_avif,quality_auto/065e6b_dd1a8624bd5b40c9848aeec671ed811b.png",
				"$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6",
				countryRepository.findByName("Germany"),
				1569,
				accountTypeRepository.findByType("USER"),
				new HashSet<AppUser>(),
				new HashSet<AppUser>()
			);
			appUserRepository.save(user1);
			appUserRepository.save(user2);
			appUserRepository.save(user3);


			log.info("Bilbo follows Frodo and Gandalf...");
			user1.getFollowing().add(user2);
			user1.getFollowing().add(user3);
			user2.getFollowers().add(user1);
			user3.getFollowers().add(user1);
			appUserRepository.save(user1);
			appUserRepository.save(user2);
			appUserRepository.save(user3);

			
			log.info("Add some example books to db...");
			Book book1 = new Book(
				"Harry Potter and the Half-Blood Prince",
				"J.K. Rowling",
				2005,
				"Harry Potter and the Half-Blood Prince is a fantasy novel written by British author J. K. Rowling. It is the sixth and penultimate novel in the Harry Potter series, and takes place during Harry Potter's sixth year at the wizard school Hogwarts. The novel reveals events from the early life of Lord Voldemort, and chronicles Harry's preparations for the final battle against him.",
				"0-7475-8108-8",
				607,
				"English",
				"https://upload.wikimedia.org/wikipedia/en/b/b5/Harry_Potter_and_the_Half-Blood_Prince_cover.png"
			);
			bookRepository.save(book1);


			log.info("Add a book to user...");
			usersBooksRepository.save(new UsersBooks(user2, book1, LocalDate.now()));


			log.info("Add review for previous book...");
			reviewRepository.save(new Review(user2, book1, LocalDate.now(), "Very good! Snape is the best!", 5));

		};

	}

}
