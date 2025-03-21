/*
package sienimetsa.sienimetsa_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class usertest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserRegistration() throws Exception {
        // Updated user JSON matching the expected DTO fields
        String userJson = "{\"email\":\"test@example.com\",\"password\":\"password123\",\"username\":\"JohnDoe\",\"phone\":\"1234567890\",\"country\":\"Finland\"}";

        mockMvc.perform(post("/mobile/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())  // Expect a 201 status (Created)
                .andExpect(jsonPath("$").value("User registered successfully!"));  // Adjusted for a simple success message
    }
}
*/