package sienimetsa.sienimetsa_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.Mushroom;

@SpringBootApplication
public class SienimetsaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SienimetsaBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo ( 
		AppuserRepository appuserRepository, 
		MushroomRepository mushroomRepository, 
		FindingRepository findingRepository){

		return (args) -> {
			Appuser appuser1 = new Appuser(
				"MehuLaatikko", 
				"$2a$10$3Zz9Zz9Zz9Zz9Zz9Zz9Zu",
				"358401234567",
				"real@email.com",
				"Finland");

			Appuser appuser2 = new Appuser(
				"KuusenKäpy", 
				"$2a$10$3Zz9Zz9Zz9Zz9Zz9Zz9Zu",
				"358401234567",
				"test@lookout.com",
				"Finland");

			appuserRepository.save(appuser1);
			appuserRepository.save(appuser2);

			Mushroom mushroom1 = new Mushroom(
				"Amanita muscaria",
				"High", 
				"Red", 
				"Free",
				"Convex", 
				"Bitter");

			Mushroom mushroom2 = new Mushroom(
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
