package backend25.bookedin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


import backend25.bookedin.model.AppUserRepository;

@SpringBootTest
class BookedinApplicationTests {

	private final AppUserRepository appUserRepository;

	@Autowired
	public BookedinApplicationTests(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void testDbConnection() {
		assertThat(appUserRepository).isNotNull();
		assertThat(appUserRepository.count()).isNotNull();
	}

}
