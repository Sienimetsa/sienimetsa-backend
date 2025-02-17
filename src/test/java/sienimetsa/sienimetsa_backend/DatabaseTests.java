/*
package sienimetsa.sienimetsa_backend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;
import sienimetsa.sienimetsa_backend.domain.Mushroompic;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class DatabaseTests {

    @Autowired
    private MushroomRepository mushroomRepository;

    @Test
    public void SFMushroom() {
        
        Mushroom mushroom = new Mushroom(
				Mushroompic.mp2,
				"TastyCap",
				"High", 
				"Brown", 
				"Free",
				"Convex", 
				"None");

        // Save the Mushroom to the repository
        Mushroom savedMushroom = mushroomRepository.save(mushroom);

        // Retrieve the Mushroom by mname (custom query method)
        Mushroom foundMushroom = mushroomRepository.findByMname("Amanita");

        // Assert that the saved and found mushroom are the same
        assertThat(foundMushroom).isNotNull();
        assertThat(foundMushroom.getMname()).isEqualTo(savedMushroom.getMname());
    }
}
    */