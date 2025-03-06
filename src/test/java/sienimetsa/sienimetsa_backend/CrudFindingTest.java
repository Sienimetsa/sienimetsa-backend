package sienimetsa.sienimetsa_backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import sienimetsa.sienimetsa_backend.domain.*;
import sienimetsa.sienimetsa_backend.web.EntityController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        MockitoAnnotations.openMocks(this);
        
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders.standaloneSetup(entityController).build();
    }
    //lisää findingin
    @Test
    public void testAddFinding() throws Exception {
        Finding finding = new Finding(new Appuser(), new Mushroom(), LocalDateTime.now(), "Helsinki", "Test notes");

        Mockito.when(frepository.save(any(Finding.class))).thenReturn(finding);

        mockMvc.perform(post("/findings/addfinding")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(finding)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Helsinki"))
                .andExpect(jsonPath("$.notes").value("Test notes"));
    }
    //päivittää findingin
    @Test
    public void testUpdateFinding() throws Exception {
        Long findingId = 1L;
        Finding existingFinding = new Finding(new Appuser(), new Mushroom(), LocalDateTime.now(), "Espoo", "Old notes");
        Finding updatedFinding = new Finding(new Appuser(), new Mushroom(), LocalDateTime.now(), "Helsinki", "New notes");

        Mockito.when(frepository.findById(findingId)).thenReturn(Optional.of(existingFinding));
        Mockito.when(frepository.save(any(Finding.class))).thenReturn(updatedFinding);

        mockMvc.perform(put("/findings/updatefinding/{id}", findingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFinding)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Helsinki"))
                .andExpect(jsonPath("$.notes").value("New notes"));
    }
    //etsii finding käyttäjän mukaan
    @Test
    public void testFindingsByUser() throws Exception {
        Long userId = 1L;
        Appuser user = new Appuser();
        user.setU_id(userId);
        Finding finding = new Finding(user, new Mushroom(), LocalDateTime.now(), "Tampere", "Forest find");

        Mockito.when(urepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(frepository.findByAppuser(user)).thenReturn(List.of(finding));

        mockMvc.perform(get("/findings/userfindings/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Tampere"))
                .andExpect(jsonPath("$[0].notes").value("Forest find"));
    }
    //poistaa findingin
    @Test
    public void testDeleteFinding() throws Exception {
        Long findingId = 1L;

        Mockito.when(frepository.existsById(findingId)).thenReturn(true);
        Mockito.doNothing().when(frepository).deleteById(findingId);

        mockMvc.perform(delete("/findings/deletefinding/{id}", findingId))
                .andExpect(status().isNoContent());
    }
}