/*
package sienimetsa.sienimetsa_backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class RestEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
    }

    //käyttää mockUseria koska sivulle pitää olla kirjautunut sisään
    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testMushroomsEndpoint() throws Exception {
        //GET pyyntö /mushrooms sivulle
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/mushrooms"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Tarkistaa että vastaus sisältää odotetun datan
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }
}
*/