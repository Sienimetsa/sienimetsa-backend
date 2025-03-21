/* 

package sienimetsa.sienimetsa_backend;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;
import sienimetsa.sienimetsa_backend.web.EntityController;

@ExtendWith(MockitoExtension.class)
public class CrudFindingTest {

    private MockMvc mockMvc;

    @Mock
    private AppuserRepository urepository;

    @Mock
    private FindingRepository frepository;

    @Mock
    private MushroomRepository mrepository;

    @InjectMocks
    private EntityController entityController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc = MockMvcBuilders.standaloneSetup(entityController).build();
    }

    @Test
    public void testAddFinding() throws Exception {
        Appuser user = new Appuser("testUser", "passwordHash", "123456789", "test@example.com",
                "TestCountry", "blue", "profilePic.png", 1);
        user.setU_id(1L);

        Mushroom mushroom = new Mushroom(1, "Shiitake", "low", "brown", "free", "convex", "savory");
        mushroom.setM_id(1L);

        LocalDateTime fixedDate = LocalDateTime.of(2023, 1, 1, 12, 0);
        Finding finding = new Finding(user, mushroom, fixedDate, "Helsinki", "Test notes", "image.png");

        org.mockito.Mockito.when(frepository.save(any(Finding.class))).thenReturn(finding);

        mockMvc.perform(post("/apu/newfinding")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(finding)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value("Helsinki"))
                .andExpect(jsonPath("$.notes").value("Test notes"));
    }

    @Test
    public void testUpdateFinding() throws Exception {
        Long findingId = 1L;
        Appuser user = new Appuser("testUser", "passwordHash", "123456789", "test@example.com",
                "TestCountry", "blue", "profilePic.png", 1);
        user.setU_id(1L);

        Mushroom mushroom = new Mushroom(1, "Shiitake", "low", "brown", "free", "convex", "savory");
        mushroom.setM_id(1L);

        Finding existingFinding = new Finding(user, mushroom, LocalDateTime.now(), "Espoo", "Old notes", "image.png");
        Finding updatedFinding = new Finding(user, mushroom, LocalDateTime.now(), "Helsinki", "New notes", "mutsis.fi");

        org.mockito.Mockito.when(frepository.findById(findingId)).thenReturn(Optional.of(existingFinding));
        org.mockito.Mockito.when(frepository.save(any(Finding.class))).thenReturn(updatedFinding);

        mockMvc.perform(put("/apu/editfinding/{id}", findingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFinding)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Helsinki"))
                .andExpect(jsonPath("$.notes").value("New notes"));
    }

    @Test
    public void testFindingsByUser() throws Exception {
        Long userId = 1L;
        Appuser user = new Appuser();
        user.setU_id(userId);
        Finding finding = new Finding(user, new Mushroom(), LocalDateTime.now(), "Tampere", "Forest find", "image.png");

        Mockito.when(urepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(frepository.findByAppuser(user)).thenReturn(List.of(finding));

        mockMvc.perform(get("/apu/userfindings/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Tampere"))
                .andExpect(jsonPath("$[0].notes").value("Forest find"));
    }

    @Test
    public void testDeleteFinding() throws Exception {
        Long findingId = 1L;

        Mockito.when(frepository.existsById(findingId)).thenReturn(true);
        Mockito.doNothing().when(frepository).deleteById(findingId);

        mockMvc.perform(delete("/apu/deletefinding/{id}", findingId))
                .andExpect(status().isNoContent());
    }
}

*/