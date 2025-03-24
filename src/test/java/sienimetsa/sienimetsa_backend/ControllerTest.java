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

    @Test
    void testWebControllerEndpoints() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/index"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/frontpage"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/mushrooms"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/findings"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testApuControllerEndpoints() throws Exception {
        mockMvc.perform(get("/apu/"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/apu/allusers"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/apu/allappusers"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/apu/allmushrooms"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/apu/allfindings"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/apu/allprofiles"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void testChatControllerWithMockUser() throws Exception {
        mockMvc.perform(get("/topic/publicChat"))
                .andExpect(status().is4xxClientError()); // Odotetaan virhettä
    }

}
