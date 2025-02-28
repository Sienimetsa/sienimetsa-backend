package sienimetsa.sienimetsa_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;
import sienimetsa.sienimetsa_backend.domain.User;
import sienimetsa.sienimetsa_backend.domain.UserRepository;


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
			
				"MehuLaatikko", 
				encodedPassword1,
				"358401234568",
				"real@email.com",
				"Finland","pink","pp1", 1);

			Appuser appuser2 = new Appuser(
		
				"KuusenKäpy", 
				encodedPassword2,
				"358401234567",
				"test@lookout.com",
				"Finland","black","pp1", 1);

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
				1,
				"Amanita muscaria",
				"High", 
				"Blue", 
				"Free",
				"Convex", 
				"Bitter");

			Mushroom mushroom2 = new Mushroom(
				2,
				"Boletus edulis",
				"High", 
				"Brown", 
				"Free",
				"Convex", 
				"None");

			Mushroom mushroom3 = new Mushroom(
				3,
				"Cantharellus cibarius",
				"Low", 
				"Yellow", 
				"Free",
				"Convex", 
				"None");

			Mushroom mushroom4 = new Mushroom(
				4,
				"Agaricus bisporus",
				"Low", 
				"White", 
				"Free",
				"Convex", 
				"None");

			Mushroom mushroom5 = new Mushroom(
				5,
				"Russula emetica",
				"High", 
				"Red", 
				"Free",
				"Convex", 
				"Bitter");

			Mushroom mushroom6 = new Mushroom(
				6,
				"Hypholoma fasciculare",
				"High", 
				"Green", 
				"Free",
				"Convex", 
				"Bitter");

			Mushroom mushroom7 = new Mushroom(
				7,
				"Clitocybe dealbata",
				"High", 
				"White", 
				"Free",
				"Convex", 
				"Bitter");
			
			Mushroom mushroom8 = new Mushroom(
				8,
				"Agaricus xanthodermus",
				"High", 
				"Yellow", 
				"Free",
				"Convex", 
				"Bitter");
			
			Mushroom mushroom9 = new Mushroom(
				9,
				"Boletus satanas",
				"High", 
				"Red", 
				"Free",
				"Convex", 
				"Bitter");
			
			Mushroom mushroom10 = new Mushroom
				(10,
				"Clitocybe rivulosa",
				"High", 
				"White", 
				"Free",
				"Convex", 
				"Bitter");
			
			mushroomRepository.save(mushroom1);
			mushroomRepository.save(mushroom2);
			mushroomRepository.save(mushroom3);
			mushroomRepository.save(mushroom4);
			mushroomRepository.save(mushroom5);
			mushroomRepository.save(mushroom6);
			mushroomRepository.save(mushroom7);
			mushroomRepository.save(mushroom8);
			mushroomRepository.save(mushroom9);
			mushroomRepository.save(mushroom10);
		

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

			Finding finding3 = new Finding(
				appuser1,
				mushroom2,
				java.time.LocalDateTime.now(),
				"Espoo",
				"Found in the forest near the lake");

			findingRepository.save(finding1);
			findingRepository.save(finding2);
			findingRepository.save(finding3);

		};
	};
}