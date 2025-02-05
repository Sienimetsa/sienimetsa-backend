package sienimetsa.sienimetsa_backend;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void adminCanAccessAdminPage() throws Exception {
        mockMvc.perform(get("/frontpage")
               .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
               .andExpect(status().isOk());
    }

    @Test
    public void userCanAccessPage() throws Exception {
        mockMvc.perform(get("/frontpage")
               .with(SecurityMockMvcRequestPostProcessors.user("user").roles("USER")))
               .andExpect(status().isOk());
    }
}
