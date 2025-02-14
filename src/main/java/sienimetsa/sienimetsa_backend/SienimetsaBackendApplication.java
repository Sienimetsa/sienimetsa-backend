package sienimetsa.sienimetsa_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;
import sienimetsa.sienimetsa_backend.domain.Mushroompic;
import sienimetsa.sienimetsa_backend.domain.User;
import sienimetsa.sienimetsa_backend.domain.UserRepository;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.ProfileIcon;

@SpringBootApplication
public class SienimetsaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SienimetsaBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo ( 
		AppuserRepository appuserRepository, 
		UserRepository userRepository,
		MushroomRepository mushroomRepository, 
		FindingRepository findingRepository){

		return (args) -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword1 = encoder.encode("mehu");
			String encodedPassword2 = encoder.encode("kuusi");
			Appuser appuser1 = new Appuser(
				ProfileIcon.pp1,
				"MehuLaatikko", 
				encodedPassword1,
				"358401234567",
				"real@email.com",
				"Finland");

			Appuser appuser2 = new Appuser(
				ProfileIcon.pp2,
				"KuusenKäpy", 
				encodedPassword2,
				"358401234567",
				"test@lookout.com",
				"Finland");

			appuserRepository.save(appuser1);
			appuserRepository.save(appuser2);

	
        String encodedAdminPassword = encoder.encode("admin");
        String encodedUserPassword = encoder.encode("user");

        // Create users with encoded passwords
        User user1 = new User("admin", encodedAdminPassword );
        User user2 = new User("user", encodedUserPassword );

        userRepository.save(user1);
        userRepository.save(user2);
			
			Mushroom mushroom1 = new Mushroom(
				Mushroompic.mp1,
				"Amanita muscaria",
				"High", 
				"Red", 
				"Free",
				"Convex", 
				"Bitter");

			Mushroom mushroom2 = new Mushroom(
				Mushroompic.mp2,
				"Boletus edulis",
				"High", 
				"Brown", 
				"Free",
				"Convex", 
				"None");
			
			mushroomRepository.save(mushroom1);
			mushroomRepository.save(mushroom2);

			Finding finding1 = new Finding(
				appuser1,
				mushroom1,
				java.time.LocalDateTime.now(),
				"Espoo",
				"Found in the forest near the lake");
			
			Finding finding2 = new Finding(
				appuser2,
				mushroom2,
				java.time.LocalDateTime.now(),
				"Vantaa",
				"Found in the forest near the lake");

			findingRepository.save(finding1);
			findingRepository.save(finding2);
		};
	};

}
