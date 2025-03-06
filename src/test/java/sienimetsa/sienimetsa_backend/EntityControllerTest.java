package sienimetsa.sienimetsa_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;
import sienimetsa.sienimetsa_backend.web.EntityController;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FindingRepository frepository;

    @Mock
    private AppuserRepository urepository;

    @Mock
    private MushroomRepository mrepository;

    @InjectMocks
    private EntityController entityController;

    @Autowired
    private ObjectMapper objectMapper;

    private Finding mockFinding;
    private Appuser mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new Appuser();
        mockUser.setU_id(1L);
        mockUser.setUsername("testuser");

        mockFinding = new Finding();
        mockFinding.setF_Id(1L);
        mockFinding.setAppuser(mockUser);
        mockFinding.setCity("Helsinki");
        mockFinding.setNotes("Test notes");
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void createFindingTest() throws Exception {
        when(frepository.save(any(Finding.class))).thenReturn(mockFinding);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/findings/addfinding")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(mockFinding));
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.f_Id").value(1))
                .andExpect(jsonPath("$.city").value("Helsinki"))
                .andExpect(jsonPath("$.notes").value("Test notes"));
    }

    @Test
    void getFindingByIdTest() throws Exception {
        when(frepository.findById(1L)).thenReturn(java.util.Optional.of(mockFinding));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/findings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.f_Id").value(1))
                .andExpect(jsonPath("$.city").value("Helsinki"))
                .andExpect(jsonPath("$.notes").value("Test notes"));
    }

    @Test
    void getAllFindingsTest() throws Exception {
        when(frepository.findAll()).thenReturn(java.util.List.of(mockFinding));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/findings/allfindings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].f_Id").value(1))
                .andExpect(jsonPath("$[0].city").value("Helsinki"))
                .andExpect(jsonPath("$[0].notes").value("Test notes"));
    }

    @Test
    void updateFindingTest() throws Exception {
        Finding updatedFinding = new Finding(mockUser, null, mockFinding.getF_time(), "Espoo", "Updated notes");
        updatedFinding.setF_Id(1L);
        when(frepository.findById(1L)).thenReturn(java.util.Optional.of(mockFinding));
        when(frepository.save(any(Finding.class))).thenReturn(updatedFinding);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/findings/editfinding/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedFinding)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Espoo"))
                .andExpect(jsonPath("$.notes").value("Updated notes"));
    }

    @Test
    void deleteFindingTest() throws Exception {
        when(frepository.existsById(1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/findings/deletefinding/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteFindingNotFoundTest() throws Exception {
        when(frepository.existsById(1L)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/findings/deletefinding/1"))
                .andExpect(status().isNotFound());
    }
}
