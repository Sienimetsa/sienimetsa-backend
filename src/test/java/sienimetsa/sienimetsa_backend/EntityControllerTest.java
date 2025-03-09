package sienimetsa.sienimetsa_backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.security.enabled=false")
public class EntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FindingRepository findingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Finding testFinding;

    @BeforeEach
    public void setUp() {
        // Set up a test finding before each test
        testFinding = new Finding();
        testFinding.setCity("Helsinki");
        testFinding.setNotes("Mushroom found near a forest trail.");
        // You should set other properties as needed, for example, user and mushroom
    }

    @Test
    public void testCreateFinding() throws Exception {
        // POST request to create a new finding
        mockMvc.perform(post("/api/findings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testFinding)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value("Helsinki"))
                .andExpect(jsonPath("$.notes").value("Mushroom found near a forest trail."));
    }

    @Test
    public void testGetFindingById() throws Exception {
        // Save a finding first
        Finding savedFinding = findingRepository.save(testFinding);

        // GET request to fetch the finding by ID
        mockMvc.perform(get("/api/findings/{id}", savedFinding.getF_Id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Helsinki"))
                .andExpect(jsonPath("$.notes").value("Mushroom found near a forest trail."));
    }

    @Test
    public void testGetAllFindings() throws Exception {
        // Save a finding before testing
        findingRepository.save(testFinding);

        // GET request to fetch all findings
        mockMvc.perform(get("/api/findings/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Helsinki"))
                .andExpect(jsonPath("$[0].notes").value("Mushroom found near a forest trail."));
    }

    @Test
    public void testUpdateFinding() throws Exception {
        // Save a finding first
        Finding savedFinding = findingRepository.save(testFinding);
        savedFinding.setCity("Espoo");

        // PUT request to update the existing finding
        mockMvc.perform(put("/api/findings/{id}", savedFinding.getF_Id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedFinding)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Espoo"));
    }

    @Test
    public void testDeleteFinding() throws Exception {
        // Save a finding before testing
        Finding savedFinding = findingRepository.save(testFinding);

        // DELETE request to delete the finding
        mockMvc.perform(delete("/api/findings/{id}", savedFinding.getF_Id()))
                .andExpect(status().isNoContent());

        // Verify it is deleted
        mockMvc.perform(get("/api/findings/{id}", savedFinding.getF_Id()))
                .andExpect(status().isNotFound());
    }
}
