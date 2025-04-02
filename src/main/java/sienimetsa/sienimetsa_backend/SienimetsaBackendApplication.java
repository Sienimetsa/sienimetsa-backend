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
					"Finland", "#9B59B6", "pp1", 1,0);

			Appuser appuser2 = new Appuser(

					"KuusenKäpy",
					encodedPassword2,
					"358401234567",
					"test@lookout.com",
					"Finland", "#E67E22", "pp1", 1,0);

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
                    "A highly toxic mushroom with a bright red cap and white spots. Found in forests, especially under birch, pine, and spruce trees.");

            Mushroom mushroom2 = new Mushroom(
                    2,
                    "Boletus edulis",
                    "Porcini, King bolete",
                    "High",
                    "Brown",
                    "Free",
                    "Convex",
                    "None",
                    "A highly sought-after edible mushroom with a thick, brown cap and white pores. Known for its rich, nutty flavor, it is commonly used in soups, sauces, and risottos. Found in both coniferous and deciduous forests.");

            Mushroom mushroom3 = new Mushroom(
                    3,
                    "Cantharellus cibarius",
                    "Chanterelle",
                    "Low",
                    "Yellow",
                    "Free",
                    "Convex",
                    "None",
                    "A prized edible mushroom with a bright yellow color and a fruity aroma. It has a delicate, mild flavor and is commonly used in gourmet dishes. Often found in moist forests, especially near oak, birch, and conifer trees.");

            Mushroom mushroom4 = new Mushroom(
                    4,
                    "Agaricus bisporus",
                    "Button mushroom",
                    "Low",
                    "White",
                    "Free",
                    "Convex",
                    "None",
                    "A common and popular edible mushroom, usually found in grocery stores. It has a mild flavor and is often used in salads, soups, and as a topping for pizzas. It grows in rich, moist soil, often in shaded areas.");

            Mushroom mushroom5 = new Mushroom(
                    5,
                    "Russula emetica",
                    "The sickener",
                    "High",
                    "Red",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A toxic mushroom that is red in color, with a bitter taste. It is dangerous to consume as it can cause severe nausea and vomiting. Found in forests, particularly in coniferous areas.");

            Mushroom mushroom6 = new Mushroom(
                    6,
                    "Hypholoma fasciculare",
                    "Sulphur tuft",
                    "High",
                    "Green",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A toxic mushroom with bright greenish-yellow caps that often grow in clusters on decaying wood. It has a sulfur-like odor and is not edible, causing gastrointestinal distress if consumed.");

            Mushroom mushroom7 = new Mushroom(
                    7,
                    "Clitocybe dealbata",
                    "Ivory funnel",
                    "High",
                    "White",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A toxic mushroom with white, funnel-shaped caps. It is often found in grassy areas and can cause serious poisoning, with symptoms such as nausea, vomiting, and diarrhea. Not recommended for consumption.");

            Mushroom mushroom8 = new Mushroom(
                    8,
                    "Agaricus xanthodermus",
                    "Yellow-staining mushroom",
                    "High",
                    "Yellow",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A toxic mushroom that produces a yellow stain when damaged. It has a strong chemical odor and should not be consumed. Eating it can cause stomach cramps and vomiting.");

            Mushroom mushroom9 = new Mushroom(
                    9,
                    "Boletus satanas",
                    "Satan's bolete",
                    "High",
                    "Red",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A highly toxic mushroom that resembles the edible boletes but has a bright red coloration on the cap and stem. It can cause severe gastrointestinal symptoms and should never be consumed.");

            Mushroom mushroom10 = new Mushroom(
                    10,
                    "Clitocybe rivulosa",
                    "Fool's funnel",
                    "High",
                    "White",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A highly toxic mushroom with a white funnel-shaped cap. It can cause poisoning if ingested, leading to symptoms like vomiting, diarrhea, and even death. It is often mistaken for edible varieties of funnel mushrooms.");

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

            // new ones

            Mushroom mushroom14 = new Mushroom(
                    14,
                    "Lactarius torminosus",
                    "Woolly milkcap",
                    "low",
                    "pink",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "A pinkish mushroom with a woolly cap, commonly found in birch forests. Edible when cooked properly.");

            Mushroom mushroom15 = new Mushroom(
                    15,
                    "Amanita phalloides",
                    "Death cap",
                    "high",
                    "green",
                    "Free",
                    "Convex",
                    "Mild",
                    "A highly toxic mushroom with a greenish cap, found in deciduous forests. Causes severe liver damage.");

            Mushroom mushroom16 = new Mushroom(
                    16,
                    "Cantharellus tubaeformis",
                    "Yellowfoot",
                    "low",
                    "yellow",
                    "Decurrent",
                    "Funnel-shaped",
                    "Mild",
                    "A small yellow mushroom with a hollow stem, often found in mossy coniferous forests.");

            Mushroom mushroom17 = new Mushroom(
                    17,
                    "Russula cyanoxantha",
                    "Charcoal burner",
                    "low",
                    "purple",
                    "Free",
                    "Convex",
                    "Mild",
                    "A purple-capped mushroom with a mild taste, commonly found in mixed forests.");

            Mushroom mushroom18 = new Mushroom(
                    18,
                    "Boletus pinophilus",
                    "Pine bolete",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A large brown mushroom with a thick stem, often found near pine trees.");

            Mushroom mushroom19 = new Mushroom(
                    19,
                    "Agaricus arvensis",
                    "Horse mushroom",
                    "low",
                    "white",
                    "Free",
                    "Convex",
                    "Mild",
                    "A large white mushroom with a pleasant taste, commonly found in grasslands and meadows.");

            Mushroom mushroom20 = new Mushroom(
                    20,
                    "Cortinarius caperatus",
                    "Gypsy mushroom",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A brownish mushroom with a wrinkled cap, found in coniferous and mixed forests.");

            Mushroom mushroom21 = new Mushroom(
                    21,
                    "Clitocybe odora",
                    "Aniseed funnel",
                    "low",
                    "blue-green",
                    "Decurrent",
                    "Convex",
                    "Aniseed",
                    "A small blue-green mushroom with a strong aniseed smell, found in coniferous forests.");

            Mushroom mushroom22 = new Mushroom(
                    22,
                    "Leccinum scabrum",
                    "Brown birch bolete",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A brown mushroom with a scaly stem, commonly found near birch trees.");

            Mushroom mushroom23 = new Mushroom(
                    23,
                    "Amanita rubescens",
                    "Blusher",
                    "low",
                    "pink",
                    "Free",
                    "Convex",
                    "Mild",
                    "A pinkish mushroom with red spots, found in mixed forests. Edible when cooked properly.");

            Mushroom mushroom24 = new Mushroom(
                    24,
                    "Suillus luteus",
                    "Slippery Jack",
                    "low",
                    "yellow-brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A slimy-capped mushroom found near pine trees, often in sandy soil.");

            Mushroom mushroom25 = new Mushroom(
                    25,
                    "Tricholoma matsutake",
                    "Matsutake",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Spicy",
                    "A highly prized mushroom with a spicy aroma, found in coniferous forests.");

            Mushroom mushroom26 = new Mushroom(
                    26,
                    "Hydnum repandum",
                    "Wood hedgehog",
                    "low",
                    "white",
                    "Free",
                    "Convex",
                    "Mild",
                    "A white mushroom with spiny gills, commonly found in coniferous forests.");

            Mushroom mushroom27 = new Mushroom(
                    27,
                    "Amanita citrina",
                    "False death cap",
                    "high",
                    "yellow",
                    "Free",
                    "Convex",
                    "Mild",
                    "A yellowish mushroom resembling the death cap, found in mixed forests.");

            Mushroom mushroom28 = new Mushroom(
                    28,
                    "Lactarius deliciosus",
                    "Saffron milkcap",
                    "low",
                    "orange",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "An orange mushroom with a hollow stem, often found in pine forests.");

            Mushroom mushroom29 = new Mushroom(
                    29,
                    "Gyromitra esculenta",
                    "False morel",
                    "high",
                    "brown",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A brain-shaped mushroom found in sandy soil. Highly toxic if not prepared properly.");

            Mushroom mushroom30 = new Mushroom(
                    30,
                    "Clitocybe nebularis",
                    "Clouded agaric",
                    "low",
                    "gray",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "A grayish mushroom with a cloud-like cap, found in deciduous forests.");

            Mushroom mushroom31 = new Mushroom(
                    31,
                    "Amanita pantherina",
                    "Panther cap",
                    "high",
                    "brown",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A highly toxic mushroom with a brown cap and white spots, found in mixed forests.");

            Mushroom mushroom32 = new Mushroom(
                    32,
                    "Lactarius rufus",
                    "Rufous milkcap",
                    "low",
                    "red-brown",
                    "Decurrent",
                    "Convex",
                    "Spicy",
                    "A reddish-brown mushroom with a spicy taste, commonly found in coniferous forests.");

            Mushroom mushroom33 = new Mushroom(
                    33,
                    "Boletus badius",
                    "Bay bolete",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A brown mushroom with a smooth cap, often found near pine and spruce trees.");

            Mushroom mushroom34 = new Mushroom(
                    34,
                    "Russula vesca",
                    "Bare-toothed russula",
                    "low",
                    "pink",
                    "Free",
                    "Convex",
                    "Mild",
                    "A pinkish mushroom with a mild taste, found in deciduous and mixed forests.");

            Mushroom mushroom35 = new Mushroom(
                    35,
                    "Agaricus campestris",
                    "Field mushroom",
                    "low",
                    "white",
                    "Free",
                    "Convex",
                    "Mild",
                    "A common edible mushroom found in grasslands and meadows.");

            Mushroom mushroom36 = new Mushroom(
                    36,
                    "Cortinarius violaceus",
                    "Violet webcap",
                    "low",
                    "violet",
                    "Free",
                    "Convex",
                    "Mild",
                    "A striking violet mushroom found in coniferous forests.");

            Mushroom mushroom37 = new Mushroom(
                    37,
                    "Lactarius deterrimus",
                    "False saffron milkcap",
                    "low",
                    "orange",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "An orange mushroom often found near spruce trees, similar to the saffron milkcap.");

            Mushroom mushroom38 = new Mushroom(
                    38,
                    "Tricholoma equestre",
                    "Man on horseback",
                    "low",
                    "yellow",
                    "Free",
                    "Convex",
                    "Mild",
                    "A yellow mushroom with a smooth cap, found in sandy soil near pine trees.");

            Mushroom mushroom39 = new Mushroom(
                    39,
                    "Amanita fulva",
                    "Tawny grisette",
                    "low",
                    "orange-brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "An orange-brown mushroom with a smooth cap, found in mixed forests.");

            Mushroom mushroom40 = new Mushroom(
                    40,
                    "Hygrophoropsis aurantiaca",
                    "False chanterelle",
                    "low",
                    "orange",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "An orange mushroom resembling a chanterelle, commonly found in coniferous forests on decaying wood.");

            Mushroom mushroom41 = new Mushroom(
                    41,
                    "Russula aeruginea",
                    "Green russula",
                    "low",
                    "green",
                    "Free",
                    "Convex",
                    "Mild",
                    "A green-capped mushroom with a mild taste, found in mixed forests.");

            Mushroom mushroom42 = new Mushroom(
                    42,
                    "Clitocybe gibba",
                    "Common funnel",
                    "low",
                    "tan",
                    "Decurrent",
                    "Funnel-shaped",
                    "Mild",
                    "A tan-colored mushroom with a funnel-shaped cap, found in leaf litter.");

            Mushroom mushroom43 = new Mushroom(
                    43,
                    "Laccaria amethystina",
                    "Amethyst deceiver",
                    "low",
                    "purple",
                    "Free",
                    "Convex",
                    "Mild",
                    "A small purple mushroom found in coniferous and deciduous forests.");

            Mushroom mushroom44 = new Mushroom(
                    44,
                    "Amanita muscaria var. guessowii",
                    "Yellow fly agaric",
                    "high",
                    "yellow",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A yellow variant of the fly agaric, found in coniferous forests.");

            Mushroom mushroom45 = new Mushroom(
                    45,
                    "Boletus reticulatus",
                    "Summer bolete",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A brown mushroom with a net-like pattern on the stem, found in deciduous forests.");

            Mushroom mushroom46 = new Mushroom(
                    46,
                    "Clathrus archeri",
                    "Devil's fingers",
                    "high",
                    "red",
                    "None",
                    "Star-shaped",
                    "Foul",
                    "A striking red mushroom with finger-like arms and a foul smell, commonly found in decaying wood or leaf litter.");

            Mushroom mushroom47 = new Mushroom(
                    47,
                    "Lactarius helvus",
                    "Fenugreek milkcap",
                    "low",
                    "brown",
                    "Decurrent",
                    "Convex",
                    "Spicy",
                    "A brown mushroom with a strong fenugreek smell, found in wet areas.");

            Mushroom mushroom48 = new Mushroom(
                    48,
                    "Hydnellum peckii",
                    "Bleeding tooth fungus",
                    "high",
                    "white-red",
                    "Decurrent",
                    "Flat",
                    "Bitter",
                    "A striking white mushroom with red liquid droplets, found in coniferous forests.");

            Mushroom mushroom49 = new Mushroom(
                    49,
                    "Amanita vaginata",
                    "Grisette",
                    "low",
                    "gray",
                    "Free",
                    "Convex",
                    "Mild",
                    "A gray mushroom with a smooth cap, found in mixed forests.");

            Mushroom mushroom50 = new Mushroom(
                    50,
                    "Boletus luridus",
                    "Lurid bolete",
                    "high",
                    "brown",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A brown mushroom with a red stem, found in deciduous forests.");

            Mushroom mushroom51 = new Mushroom(
                    51,
                    "Amanita caesarea",
                    "Caesar's mushroom",
                    "low",
                    "orange",
                    "Free",
                    "Convex",
                    "Mild",
                    "A highly prized edible mushroom with an orange cap, found in deciduous forests.");

            Mushroom mushroom52 = new Mushroom(
                    52,
                    "Lactarius indigo",
                    "Indigo milkcap",
                    "low",
                    "blue",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "A striking blue mushroom with a mild taste, found in mixed forests.");

            Mushroom mushroom53 = new Mushroom(
                    53,
                    "Boletus aereus",
                    "Bronze bolete",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A dark brown mushroom with a smooth cap, found in deciduous forests.");

            Mushroom mushroom54 = new Mushroom(
                    54,
                    "Russula ochroleuca",
                    "Ochre brittlegill",
                    "low",
                    "yellow",
                    "Free",
                    "Convex",
                    "Mild",
                    "A yellowish mushroom with a brittle texture, found in coniferous forests.");

            Mushroom mushroom55 = new Mushroom(
                    55,
                    "Agaricus augustus",
                    "The prince",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A large brown mushroom with a pleasant almond-like smell, found in mixed forests.");

            Mushroom mushroom56 = new Mushroom(
                    56,
                    "Cortinarius semisanguineus",
                    "Surprise webcap",
                    "high",
                    "red-brown",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A reddish-brown mushroom with toxic properties, found in coniferous forests.");

            Mushroom mushroom57 = new Mushroom(
                    57,
                    "Lactarius vellereus",
                    "Fleecy milkcap",
                    "low",
                    "white",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "A large white mushroom with a velvety cap, found in deciduous forests.");

            Mushroom mushroom58 = new Mushroom(
                    58,
                    "Tricholoma terreum",
                    "Grey knight",
                    "low",
                    "gray",
                    "Free",
                    "Convex",
                    "Mild",
                    "A gray mushroom with a smooth cap, found in coniferous forests.");

            Mushroom mushroom59 = new Mushroom(
                    59,
                    "Amanita gemmata",
                    "Jewelled amanita",
                    "high",
                    "yellow",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A yellow mushroom with white spots, found in mixed forests.");

            Mushroom mushroom60 = new Mushroom(
                    60,
                    "Boletus erythropus",
                    "Scarletina bolete",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A brown mushroom with red pores, found in coniferous forests.");

            Mushroom mushroom61 = new Mushroom(
                    61,
                    "Russula xerampelina",
                    "Shrimp russula",
                    "low",
                    "purple",
                    "Free",
                    "Convex",
                    "Mild",
                    "A purple mushroom with a shrimp-like smell, found in mixed forests.");

            Mushroom mushroom62 = new Mushroom(
                    62,
                    "Clitocybe geotropa",
                    "Trooping funnel",
                    "low",
                    "tan",
                    "Decurrent",
                    "Funnel-shaped",
                    "Mild",
                    "A tan-colored mushroom with a funnel-shaped cap, found in grasslands.");

            Mushroom mushroom63 = new Mushroom(
                    63,
                    "Laccaria laccata",
                    "Deceiver",
                    "low",
                    "orange",
                    "Free",
                    "Convex",
                    "Mild",
                    "A small orange mushroom with a smooth cap, found in coniferous forests.");

            Mushroom mushroom64 = new Mushroom(
                    64,
                    "Amanita porphyria",
                    "Grey veiled amanita",
                    "high",
                    "gray",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A gray mushroom with a smooth cap, found in mixed forests.");

            Mushroom mushroom65 = new Mushroom(
                    65,
                    "Boletus subtomentosus",
                    "Yellow-cracked bolete",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A brown mushroom with a cracked cap, found in deciduous forests.");
            Mushroom mushroom66 = new Mushroom(
                    66,
                    "Hygrophorus camarophyllus",
                    "Arched woodwax",
                    "low",
                    "gray-brown",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "A rare mushroom with a smooth, waxy cap and a faintly sweet smell, typically found in coniferous forests.");

            Mushroom mushroom67 = new Mushroom(
                    67,
                    "Leccinum versipelle",
                    "Orange birch bolete",
                    "low",
                    "orange",
                    "Free",
                    "Convex",
                    "Mild",
                    "An orange-capped mushroom with a scaly stem, commonly found near birch trees.");

            Mushroom mushroom68 = new Mushroom(
                    68,
                    "Tricholoma portentosum",
                    "Zoned knight",
                    "low",
                    "gray",
                    "Free",
                    "Convex",
                    "Mild",
                    "A gray mushroom with a smooth cap, found in coniferous forests.");

            Mushroom mushroom69 = new Mushroom(
                    69,
                    "Amanita regalis",
                    "Royal fly agaric",
                    "high",
                    "brown",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A brown mushroom with white spots, found in coniferous forests.");

            Mushroom mushroom70 = new Mushroom(
                    70,
                    "Boletus radicans",
                    "Rooting bolete",
                    "high",
                    "gray",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A gray mushroom with a bitter taste, found in deciduous forests.");

            Mushroom mushroom71 = new Mushroom(
                    71,
                    "Russula virescens",
                    "Green-cracking russula",
                    "low",
                    "green",
                    "Free",
                    "Convex",
                    "Mild",
                    "A green mushroom with a cracked cap, found in deciduous forests.");

            Mushroom mushroom72 = new Mushroom(
                    72,
                    "Clitocybe clavipes",
                    "Club-foot",
                    "low",
                    "tan",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "A tan mushroom with a club-shaped stem, found in coniferous forests.");

            Mushroom mushroom73 = new Mushroom(
                    73,
                    "Laccaria proxima",
                    "Scurfy deceiver",
                    "low",
                    "orange",
                    "Free",
                    "Convex",
                    "Mild",
                    "A small orange mushroom with a scaly cap, found in mixed forests.");

            Mushroom mushroom74 = new Mushroom(
                    74,
                    "Hygrophorus olivaceoalbus",
                    "Olive waxcap",
                    "low",
                    "olive-brown",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "A slimy-capped mushroom with an olive-brown color, commonly found in coniferous forests during autumn.");

            Mushroom mushroom75 = new Mushroom(
                    75,
                    "Boletus calopus",
                    "Bitter bolete",
                    "high",
                    "brown",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A brown mushroom with a red stem, found in deciduous forests.");

            Mushroom mushroom76 = new Mushroom(
                    76,
                    "Russula rosea",
                    "Rosy russula",
                    "low",
                    "pink",
                    "Free",
                    "Convex",
                    "Mild",
                    "A pink mushroom with a mild taste, found in deciduous forests.");

            Mushroom mushroom77 = new Mushroom(
                    77,
                    "Lactarius pubescens",
                    "Bearded milkcap",
                    "low",
                    "white",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "A white mushroom with a hairy cap, found in birch forests.");

            Mushroom mushroom78 = new Mushroom(
                    78,
                    "Tricholoma sulphureum",
                    "Sulphur knight",
                    "high",
                    "yellow",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A yellow mushroom with a strong sulfur smell, found in mixed forests.");

            Mushroom mushroom79 = new Mushroom(
                    79,
                    "Amanita jacksonii",
                    "Jackson's slender amanita",
                    "low",
                    "orange-red",
                    "Free",
                    "Convex",
                    "Mild",
                    "A striking orange-red mushroom with a smooth cap, commonly found in deciduous forests in North America.");

            Mushroom mushroom80 = new Mushroom(
                    80,
                    "Sarcodon imbricatus",
                    "Shingled hedgehog",
                    "low",
                    "brown",
                    "Decurrent",
                    "Convex",
                    "Bitter",
                    "A scaly brown mushroom with spiny gills, commonly found in coniferous forests and often used for dyeing fabrics.");

            Mushroom mushroom81 = new Mushroom(
                    81,
                    "Amanita excelsa",
                    "Gray spotted amanita",
                    "high",
                    "gray",
                    "Free",
                    "Convex",
                    "Bitter",
                    "A gray mushroom with white spots, found in mixed forests.");

            Mushroom mushroom82 = new Mushroom(
                    82,
                    "Lactarius trivialis",
                    "Gray milkcap",
                    "low",
                    "gray",
                    "Decurrent",
                    "Convex",
                    "Mild",
                    "A grayish mushroom with a mild taste, commonly found in wet coniferous forests.");

            Mushroom mushroom83 = new Mushroom(
                    83,
                    "Boletus queletii",
                    "Deceiving bolete",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A brown mushroom with a smooth cap, found in deciduous forests.");

            Mushroom mushroom84 = new Mushroom(
                    84,
                    "Russula paludosa",
                    "Swamp russula",
                    "low",
                    "red",
                    "Free",
                    "Convex",
                    "Mild",
                    "A red mushroom with a mild taste, commonly found in swampy areas.");

            Mushroom mushroom85 = new Mushroom(
                    85,
                    "Agaricus silvaticus",
                    "Scaly wood mushroom",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A brown mushroom with a scaly cap, found in coniferous forests.");

            Mushroom mushroom86 = new Mushroom(
                    86,
                    "Cortinarius praestans",
                    "Giant webcap",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A large brown mushroom with a smooth cap, found in deciduous forests.");

            Mushroom mushroom87 = new Mushroom(
                    87,
                    "Lactarius turpis",
                    "Ugly milkcap",
                    "low",
                    "green-brown",
                    "Decurrent",
                    "Convex",
                    "Bitter",
                    "A greenish-brown mushroom with a bitter taste, found in coniferous forests.");

            Mushroom mushroom88 = new Mushroom(
                    88,
                    "Tricholoma flavovirens",
                    "Yellow knight",
                    "low",
                    "yellow",
                    "Free",
                    "Convex",
                    "Mild",
                    "A yellow mushroom with a smooth cap, found in sandy soil near pine trees.");

            Mushroom mushroom89 = new Mushroom(
                    89,
                    "Amanita crocea",
                    "Orange grisette",
                    "low",
                    "orange",
                    "Free",
                    "Convex",
                    "Mild",
                    "An orange mushroom with a smooth cap, found in mixed forests.");

            Mushroom mushroom90 = new Mushroom(
                    90,
                    "Boletus pulverulentus",
                    "Ink stain bolete",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A brown mushroom with a dark staining reaction, found in deciduous forests.");

            Mushroom mushroom91 = new Mushroom(
                    91,
                    "Russula integra",
                    "Entire russula",
                    "low",
                    "brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A brown mushroom with a mild taste, found in mixed forests.");

            Mushroom mushroom92 = new Mushroom(
                    92,
                    "Clitocybe fragrans",
                    "Fragrant funnel",
                    "low",
                    "white",
                    "Decurrent",
                    "Funnel-shaped",
                    "Aniseed",
                    "A small white mushroom with a strong aniseed smell, found in coniferous forests.");

            Mushroom mushroom93 = new Mushroom(
                    93,
                    "Laccaria bicolor",
                    "Bicolored deceiver",
                    "low",
                    "purple",
                    "Free",
                    "Convex",
                    "Mild",
                    "A purple mushroom with a smooth cap, found in coniferous forests.");

            Mushroom mushroom94 = new Mushroom(
                    94,
                    "Amanita submembranacea",
                    "Olive grisette",
                    "low",
                    "olive-green",
                    "Free",
                    "Convex",
                    "Mild",
                    "An olive-green mushroom with a smooth cap, found in mixed forests.");

            Mushroom mushroom95 = new Mushroom(
                    95,
                    "Boletus appendiculatus",
                    "Butter bolete",
                    "low",
                    "yellow-brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A yellow-brown mushroom with a smooth cap, found in deciduous forests.");

            Mushroom mushroom96 = new Mushroom(
                    96,
                    "Russula claroflava",
                    "Yellow swamp russula",
                    "low",
                    "yellow",
                    "Free",
                    "Convex",
                    "Mild",
                    "A yellow mushroom with a mild taste, found in swampy areas.");

            Mushroom mushroom97 = new Mushroom(
                    97,
                    "Lactarius camphoratus",
                    "Curry milkcap",
                    "low",
                    "brown",
                    "Decurrent",
                    "Convex",
                    "Spicy",
                    "A brown mushroom with a strong curry-like smell, found in coniferous forests.");

            Mushroom mushroom98 = new Mushroom(
                    98,
                    "Tricholoma pessundatum",
                    "Red knight",
                    "low",
                    "red-brown",
                    "Free",
                    "Convex",
                    "Mild",
                    "A red-brown mushroom with a smooth cap, found in mixed forests.");

            Mushroom mushroom99 = new Mushroom(
                    99,
                    "Amanita nivalis",
                    "Snowy amanita",
                    "low",
                    "white",
                    "Free",
                    "Convex",
                    "Mild",
                    "A white mushroom found in alpine and arctic regions, often near birch trees.");

            Mushroom mushroom100 = new Mushroom(
                    100,
                    "Boletus regius",
                    "Royal bolete",
                    "low",
                    "pink",
                    "Free",
                    "Convex",
                    "Mild",
                    "A pink mushroom with a smooth cap, found in deciduous forests.");

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
            mushroomRepository.save(mushroom14);
            mushroomRepository.save(mushroom15);
            mushroomRepository.save(mushroom16);
            mushroomRepository.save(mushroom17);
            mushroomRepository.save(mushroom18);
            mushroomRepository.save(mushroom19);
            mushroomRepository.save(mushroom20);
            mushroomRepository.save(mushroom21);
            mushroomRepository.save(mushroom22);
            mushroomRepository.save(mushroom23);
            mushroomRepository.save(mushroom24);
            mushroomRepository.save(mushroom25);
            mushroomRepository.save(mushroom26);
            mushroomRepository.save(mushroom27);
            mushroomRepository.save(mushroom28);
            mushroomRepository.save(mushroom29);
            mushroomRepository.save(mushroom30);
            mushroomRepository.save(mushroom31);
            mushroomRepository.save(mushroom32);
            mushroomRepository.save(mushroom33);
            mushroomRepository.save(mushroom34);
            mushroomRepository.save(mushroom35);
            mushroomRepository.save(mushroom36);
            mushroomRepository.save(mushroom37);
            mushroomRepository.save(mushroom38);
            mushroomRepository.save(mushroom39);
            mushroomRepository.save(mushroom40);
            mushroomRepository.save(mushroom41);
            mushroomRepository.save(mushroom42);
            mushroomRepository.save(mushroom43);
            mushroomRepository.save(mushroom44);
            mushroomRepository.save(mushroom45);
            mushroomRepository.save(mushroom46);
            mushroomRepository.save(mushroom47);
            mushroomRepository.save(mushroom48);
            mushroomRepository.save(mushroom49);
            mushroomRepository.save(mushroom50);
            mushroomRepository.save(mushroom51);
            mushroomRepository.save(mushroom52);
            mushroomRepository.save(mushroom53);
            mushroomRepository.save(mushroom54);
            mushroomRepository.save(mushroom55);
            mushroomRepository.save(mushroom56);
            mushroomRepository.save(mushroom57);
            mushroomRepository.save(mushroom58);
            mushroomRepository.save(mushroom59);
            mushroomRepository.save(mushroom60);
            mushroomRepository.save(mushroom61);
            mushroomRepository.save(mushroom62);
            mushroomRepository.save(mushroom63);
            mushroomRepository.save(mushroom64);
            mushroomRepository.save(mushroom65);
            mushroomRepository.save(mushroom66);
            mushroomRepository.save(mushroom67);
            mushroomRepository.save(mushroom68);
            mushroomRepository.save(mushroom69);
            mushroomRepository.save(mushroom70);
            mushroomRepository.save(mushroom71);
            mushroomRepository.save(mushroom72);
            mushroomRepository.save(mushroom73);
            mushroomRepository.save(mushroom74);
            mushroomRepository.save(mushroom75);
            mushroomRepository.save(mushroom76);
            mushroomRepository.save(mushroom77);
            mushroomRepository.save(mushroom78);
            mushroomRepository.save(mushroom79);
            mushroomRepository.save(mushroom80);
            mushroomRepository.save(mushroom81);
            mushroomRepository.save(mushroom82);
            mushroomRepository.save(mushroom83);
            mushroomRepository.save(mushroom84);
            mushroomRepository.save(mushroom85);
            mushroomRepository.save(mushroom86);
            mushroomRepository.save(mushroom87);
            mushroomRepository.save(mushroom88);
            mushroomRepository.save(mushroom89);
            mushroomRepository.save(mushroom90);
            mushroomRepository.save(mushroom91);
            mushroomRepository.save(mushroom92);
            mushroomRepository.save(mushroom93);
            mushroomRepository.save(mushroom94);
            mushroomRepository.save(mushroom95);
            mushroomRepository.save(mushroom96);
            mushroomRepository.save(mushroom97);
            mushroomRepository.save(mushroom98);
            mushroomRepository.save(mushroom99);
            mushroomRepository.save(mushroom100);


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

}