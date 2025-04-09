package sienimetsa.sienimetsa_backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String[] WEB_CONTROLLER_ENDPOINTS = {
        "/",
        "/index",
        "/frontpage",
        "/users",
        "/mushrooms",
        "/findings"
    };

    private static final String[] APU_CONTROLLER_ENDPOINTS = {
        "/apu/",
        "/apu/allusers",
        "/apu/allappusers",
        "/apu/allmushrooms",
        "/apu/allfindings",
        "/apu/allprofiles"
    };

    @Test
    void testWebControllerEndpoints() throws Exception {
        for (String endpoint : WEB_CONTROLLER_ENDPOINTS) {
            mockMvc.perform(get(endpoint))
                    .andExpect(status().is3xxRedirection());
        }
    }

    @Test
    void testApuControllerEndpoints() throws Exception {
        for (String endpoint : APU_CONTROLLER_ENDPOINTS) {
                mockMvc.perform(get(endpoint))
                        .andExpect(status().isOk());
            }
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void testChatControllerWithMockUser() throws Exception {
        mockMvc.perform(get("/topic/publicChat"))
                .andExpect(status().is4xxClientError()); // Expecting client error as user is not authorized
    }
}