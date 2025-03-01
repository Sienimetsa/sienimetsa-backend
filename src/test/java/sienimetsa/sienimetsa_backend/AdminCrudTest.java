// package sienimetsa.sienimetsa_backend;
// import static org.assertj.core.api.Assertions.assertThat;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.transaction.annotation.Transactional;

// import sienimetsa.sienimetsa_backend.domain.Appuser;
// import sienimetsa.sienimetsa_backend.domain.AppuserRepository;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Transactional
// public class AdminCrudTest {

//     @Autowired
//     private TestRestTemplate restTemplate;

//     @Autowired
//     private AppuserRepository urepository;

//     //testaa käyttäjän luomista
//     @Test
//     public void testCreateUser() {
//         Appuser newUser = new Appuser();
//         newUser.setUsername("kissaaa");
//         newUser.setPasswordHash("mauu");
//         newUser.setPhone("123456789");
//         newUser.setEmail("maumaumau@egmail.com");
//         newUser.setCountry("Finland");

//         ResponseEntity<Appuser> response = restTemplate.postForEntity("/saveu", newUser, Appuser.class);
        
//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//         assertThat(urepository.findByUsername("testuser")).isNotNull();
//     }
//     //hakee kaikki käyttäjät
//     @Test
//     public void testGetUsers() {
//         ResponseEntity<String> response = restTemplate.getForEntity("/users", String.class);
//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//     }
//     //testaa muokata käyttjää
//     @Test
//     public void testEditUser() {
//         Appuser user = new Appuser("testi", "kissa", "0409602567", "maumau@gmail.com", "estonia", "pink", "pic2");
//         user = urepository.save(user);
        
//         user.setUsername("updateduser");
//         restTemplate.postForEntity("/saveu", user, Appuser.class);
        
//         Appuser updatedUser = urepository.findById(user.getU_id()).orElse(null);
//         assertThat(updatedUser).isNotNull();
//         assertThat(updatedUser.getUsername()).isEqualTo("updateduser");
//     }
//     //testaa poistaa käyttäjän
//     @Test
//     public void testDeleteUser() {
//     Appuser user = new Appuser("deleteme", "kissa", "0409602345", "deleteuser@gmail.com", "Norway", "miu", "mau");
//     user = urepository.save(user);

//     System.out.println("User created: " + user.getU_id());
//     assertThat(urepository.existsById(user.getU_id())).isTrue();
//     restTemplate.delete("/deleteu/" + user.getU_id());
//     boolean userExists = urepository.existsById(user.getU_id());
//     System.out.println("User exists after delete? " + userExists);
//     assertThat(userExists).isTrue();
// }
// }
