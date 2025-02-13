package sienimetsa.sienimetsa_backend;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import sienimetsa.sienimetsa_backend.domain.*;



@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepoTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MushroomRepository mushroomRepository; 
    @Autowired
    private FindingRepository findingRepository;
    @Autowired
    private AppuserRepository appuserRepository;

    @Test
    public void testAppuserCRUD() {
        // Create
        Appuser appuser = new Appuser(
            ProfileIcon.pp1,
            "TestPesti", 
            "SaSa2025", 
            "123456789", 
            "test@pesti.com", 
            "SuomiFinland");
        assertNotNull(appuserRepository.save(appuser));

        // Read
        assertNotNull(appuserRepository.findById(appuser.getU_id()));


        // Update
        appuser.setCountry("Guatemala");
        Appuser updated = appuserRepository.save(appuser);
        assertEquals("Guatemala", updated.getCountry());

        // Delete
        appuserRepository.delete(appuser);
        assertFalse(appuserRepository.findById(appuser.getU_id()).isPresent());
    }

    @Test
    public void testMushroomCRUD() {
        // Create
        Mushroom mushroom = new Mushroom(
            "Amanita muscaria",
            "High", 
            "Red", 
            "Free",
            "Convex", 
            "Bitter");
        assertNotNull(mushroomRepository.save(mushroom));

        // Read
        assertNotNull(mushroomRepository.findById(mushroom.getM_Id()));

        // Update
        mushroom.setMname("UpdatedMushroom");
        Mushroom updated = mushroomRepository.save(mushroom);
        assertEquals("UpdatedMushroom", updated.getMname());

        // Delete
        mushroomRepository.delete(mushroom);
        assertFalse(mushroomRepository.findById(mushroom.getM_Id()).isPresent());
    }

    @Test
    public void testFindingCRUD() {
        // Create
        Mushroom mushroom = new Mushroom(
            "Amanita muscaria",
            "High", 
            "Red", 
            "Free",
            "Convex", 
            "Bitter");
        mushroomRepository.save(mushroom);
        Appuser appuser = new Appuser(
            ProfileIcon.pp1,
            "TestPesti", 
            "SaSa2025", 
            "123456789", 
            "test@pesti.com", 
            "SuomiFinland");
        appuserRepository.save(appuser);
        
        Finding finding = new Finding(
            appuser,
            mushroom,
            java.time.LocalDateTime.now(),
            "Espoo",
            "Found in the forest near the lake");
        assertNotNull(findingRepository.save(finding));

        // Read
        assertNotNull(findingRepository.findById(finding.getF_Id()));

        // Update
        finding.setCity("NewLocation");
        Finding updated = findingRepository.save(finding);
        assertEquals("NewLocation", updated.getCity());

        // Delete
        findingRepository.delete(finding);
        assertFalse(findingRepository.findById(finding.getF_Id()).isPresent());
    }

    @Test
    public void testUserCRUD() {
        // Create
        User user = new User(
            "TestUser", 
            "EOODOJEFIJQ399UR9U1"
        );
        assertNotNull(userRepository.save(user));

        // Read
        assertNotNull(userRepository.findById(user.getId()));

        // Update
        user.setUsername("TestUsername");
        User updated = userRepository.save(user);
        assertEquals("TestUsername", updated.getUsername());

        // Delete
        userRepository.delete(user);
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }
}
