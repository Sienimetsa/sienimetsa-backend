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
import sienimetsa.sienimetsa_backend.service.LevelingService;

@SpringBootApplication
public class SienimetsaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SienimetsaBackendApplication.class, args);
	}
/*
	@Bean
	public CommandLineRunner demo(
			AppuserRepository appuserRepository,
			UserRepository userRepository,
			MushroomRepository mushroomRepository,
			FindingRepository findingRepository,
			LevelingService levelingService) {

		return (args) -> {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword1 = encoder.encode("mehu");
			String encodedPassword2 = encoder.encode("kuusi");
			Appuser appuser1 = new Appuser(

					"MehuLaatikko",
					encodedPassword1,
					"358401234568",
					"real@email.com",
					"Finland", "#9B59B6", "pp1", 1);

			Appuser appuser2 = new Appuser(

					"KuusenKäpy",
					encodedPassword2,
					"358401234567",
					"test@lookout.com",
					"Finland", "#E67E22", "pp1", 1);

			appuserRepository.save(appuser1);
			appuserRepository.save(appuser2);

			String encodedAdminPassword = encoder.encode("admin");
			String encodedUserPassword = encoder.encode("user");

			// Create users with encoded passwords
			User user1 = new User("admin", encodedAdminPassword);
			User user2 = new User("user", encodedUserPassword);

			userRepository.save(user1);
			userRepository.save(user2);

			Mushroom mushroom1 = new Mushroom(
					1,
					"Amanita muscaria",
					"Fly agaric",
					"High",
					"Blue",
					"Free",
					"Convex",
					"Bitter",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit");

			Mushroom mushroom2 = new Mushroom(
					2,
					"Boletus edulis",
					"Porcini, King bolete",
					"High",
					"Brown",
					"Free",
					"Convex",
					"None",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit");

			Mushroom mushroom3 = new Mushroom(
					3,
					"Cantharellus cibarius",
					"Chanterelle",
					"Low",
					"Yellow",
					"Free",
					"Convex",
					"None",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit");

			Mushroom mushroom4 = new Mushroom(
					4,
					"Agaricus bisporus",
					"Button mushroom",
					"Low",
					"White",
					"Free",
					"Convex",
					"None",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit");

			Mushroom mushroom5 = new Mushroom(
					5,
					"Russula emetica",
					"The sickener",
					"High",
					"Red",
					"Free",
					"Convex",
					"Bitter",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit");

			Mushroom mushroom6 = new Mushroom(
					6,
					"Hypholoma fasciculare",
					"Sulphur tuft",
					"High",
					"Green",
					"Free",
					"Convex",
					"Bitter",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit");

			Mushroom mushroom7 = new Mushroom(
					7,
					"Clitocybe dealbata",
					"Ivory funnel",
					"High",
					"White",
					"Free",
					"Convex",
					"Bitter",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit");

			Mushroom mushroom8 = new Mushroom(
					8,
					"Agaricus xanthodermus",
					"Yellow-staining mushroom",
					"High",
					"Yellow",
					"Free",
					"Convex",
					"Bitter",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit");

			Mushroom mushroom9 = new Mushroom(
					9,
					"Boletus satanas",
					"Satan's bolete",
					"High",
					"Red",
					"Free",
					"Convex",
					"Bitter",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit");

			Mushroom mushroom10 = new Mushroom(
					10,
					"Clitocybe rivulosa",
					"Fool's funnel",
					"High",
					"White",
					"Free",
					"Convex",
					"Bitter",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit");

			Mushroom mushroom11 = new Mushroom(
					11,
					"Craterellus tubaeformis",
					"winter chanterelle",
					"low",
					"Brown",
					"Decurrent",
					"Funnel-shaped",
					"Mild",
					"A popular edible mushroom found in coniferous forests, often growing in mossy areas.");

			Mushroom mushroom12 = new Mushroom(
					12,
					"Craterellus cornucopioides",
					"black trumpet",
					"low",
					"Brown",
					"Decurrent",
					"Convex to Funnel-shaped",
					"Bitter",
					"A velvety dark brown mushroom commonly found in coniferous forests, often growing on decaying wood or tree stumps.");

			Mushroom mushroom13 = new Mushroom(
					13,
					"Amanita virosa",
					"destroying angel",
					"high",
					"white",
					"Hymenium",
					"Convex or flat",
					"Mild",
					"A deadly poisonous mushroom with a pure white cap, stem, and gills. Found in forests, particularly near birch and conifers. Consumption can cause fatal liver and kidney failure.");

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
			mushroomRepository.save(mushroom11);
			mushroomRepository.save(mushroom12);
			mushroomRepository.save(mushroom13);

			Finding finding1 = new Finding(
					appuser1,
					mushroom1,
					java.time.LocalDateTime.now(),
					"Espoo",
					"Found in the forest near the lake",
					"Konsta.png");
			levelingService.processFinding(appuser1, mushroom1);

			Finding finding2 = new Finding(
					appuser2,
					mushroom2,
					java.time.LocalDateTime.now(),
					"Vantaa",
					"Found in the forest near the lake",
					"Konsta.png");
			levelingService.processFinding(appuser2, mushroom2);

			Finding finding3 = new Finding(
					appuser1,
					mushroom2,
					java.time.LocalDateTime.now(),
					"Espoo",
					"Found in the forest near the lake",
					"Konsta.png");
			levelingService.processFinding(appuser1, mushroom2);

			Finding finding4 = new Finding(
					appuser1,
					mushroom8,
					java.time.LocalDateTime.now(),
					"HESA",
					"LÖYSIN TOST LÄHELT",
					"Konsta.png");
			levelingService.processFinding(appuser1, mushroom8);

			Finding finding5 = new Finding(
					appuser2,
					mushroom5,
					java.time.LocalDateTime.now(),
					"Espoo",
					"Found in the forest near the lake",
					"Konsta.png");
			levelingService.processFinding(appuser2, mushroom5);

			Finding finding6 = new Finding(
					appuser1,
					mushroom6,
					java.time.LocalDateTime.now(),
					"TURKU",
					"LÖYSIN TOST TOISELT PUOLELT JOKKEE",
					"Konsta.png");
			levelingService.processFinding(appuser1, mushroom6);

			Finding finding7 = new Finding(
					appuser1,
					mushroom1,
					java.time.LocalDateTime.now(),
					"Espoo",
					"Oli puskan takana",
					"Konsta.png");
			levelingService.processFinding(appuser1, mushroom1);

			Finding finding8 = new Finding(
					appuser1,
					mushroom1,
					java.time.LocalDateTime.now(),
					"Vantaa",
					"karhuja paljon alueella, pitää varoa",
					"Konsta.png");
			levelingService.processFinding(appuser1, mushroom1);

			Finding finding9 = new Finding(
					appuser1,
					mushroom1,
					java.time.LocalDateTime.now(),
					"Espoo",
					"Paljon väkeä ilalla pitää mennä aamulla",
					"Konsta.png");
			levelingService.processFinding(appuser1, mushroom1);

			Finding finding10 = new Finding(
					appuser1,
					mushroom2,
					java.time.LocalDateTime.now(),
					"AAAA",
					"HULLU LÖYTÖ (<---- copilot täytti :DDD)",
					"Konsta.png");
			levelingService.processFinding(appuser1, mushroom2);

			findingRepository.save(finding1);
			findingRepository.save(finding2);
			findingRepository.save(finding3);
			findingRepository.save(finding4);
			findingRepository.save(finding5);
			findingRepository.save(finding6);
			findingRepository.save(finding7);
			findingRepository.save(finding8);
			findingRepository.save(finding9);
			findingRepository.save(finding10);
			appuserRepository.save(appuser1);
			appuserRepository.save(appuser2);

		};
	};
	*/
}