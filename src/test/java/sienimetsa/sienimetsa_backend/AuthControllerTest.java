package sienimetsa.sienimetsa_backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserProfile;
import sienimetsa.sienimetsa_backend.domain.AppuserProfileRepository;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.dto.MobileLoginRequestDTO;
import sienimetsa.sienimetsa_backend.dto.MobileSignupRequestDTO;
import sienimetsa.sienimetsa_backend.jwt.JwtUtil;
import sienimetsa.sienimetsa_backend.web.AppuserService;
import sienimetsa.sienimetsa_backend.web.AuthController;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppuserRepository userRepository;

    @Mock
    private AppuserProfileRepository profileRepository;

    @Mock
    private AppuserService appuserService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testLogin_Success() throws Exception {
        MobileLoginRequestDTO loginRequest = new MobileLoginRequestDTO("test@example.com", "password");
        Appuser user = new Appuser("username", "encodedPassword", "1234567890", "test@example.com", "Finland");

        when(appuserService.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("test@example.com")).thenReturn("jwtToken");

        mockMvc.perform(post("/mobile/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwtToken"));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        MobileLoginRequestDTO loginRequest = new MobileLoginRequestDTO("test@example.com", "wrongPassword");

        when(appuserService.getUserByEmail("test@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/mobile/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\",\"password\":\"wrongPassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }

    @Test
    public void testSignup_Success() throws Exception {
        MobileSignupRequestDTO signupRequest = new MobileSignupRequestDTO("username", "password", "1234567890", "test@example.com", "Finland");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.existsByUsername("username")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(jwtUtil.generateToken("test@example.com")).thenReturn("jwtToken");

        mockMvc.perform(post("/mobile/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"username\",\"password\":\"password\",\"phone\":\"1234567890\",\"email\":\"test@example.com\",\"country\":\"Finland\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("jwtToken"));
    }

    @Test
    public void testSignup_EmailAlreadyExists() throws Exception {
        MobileSignupRequestDTO signupRequest = new MobileSignupRequestDTO("username", "password", "1234567890", "test@example.com", "Finland");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        mockMvc.perform(post("/mobile/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"username\",\"password\":\"password\",\"phone\":\"1234567890\",\"email\":\"test@example.com\",\"country\":\"Finland\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already in use"));
    }

    @Test
    public void testSignup_UsernameAlreadyExists() throws Exception {
        MobileSignupRequestDTO signupRequest = new MobileSignupRequestDTO("username", "password", "1234567890", "test@example.com", "Finland");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.existsByUsername("username")).thenReturn(true);

        mockMvc.perform(post("/mobile/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"username\",\"password\":\"password\",\"phone\":\"1234567890\",\"email\":\"test@example.com\",\"country\":\"Finland\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username already exists"));
    }

    @Test
    public void testGetProfile_Success() throws Exception {
        // Prepare the user and profile
        Appuser user = new Appuser("username", "encodedPassword", "1234567890", "test@example.com", "Finland");
        AppuserProfile profile = new AppuserProfile(user, "#000000", "pp1");
    
        // Mock the service layer
        when(appuserService.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(profile));
    
        // Generate the token (this is what was missing)
        String token = "validJwtToken";  // Replace with actual token generation logic if necessary
        when(jwtUtil.generateToken("test@example.com")).thenReturn(token);
    
        // Perform the GET request with the Authorization header containing the token
        mockMvc.perform(get("/mobile/profile")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.profilePicture").value("pp1"))
                .andExpect(jsonPath("$.chatColor").value("#000000"));
    }
    
    

    @Test
    public void testGetProfile_UserNotFound() throws Exception {
        when(appuserService.getUserByEmail("test@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/mobile/profile")
                .principal(() -> "test@example.com"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    public void testUpdateProfile_Success() throws Exception {
        Appuser user = new Appuser("username", "encodedPassword", "1234567890", "test@example.com", "Finland");
        AppuserProfile profile = new AppuserProfile(user, "#000000", "pp1");

        when(appuserService.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(profile));

        mockMvc.perform(put("/mobile/updateProfile")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"newUsername\",\"password\":\"newPassword\",\"profilePicture\":\"newPp\",\"chatColor\":\"#FFFFFF\"}")
                .principal(() -> "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Profile updated successfully"));
    }

    @Test
    public void testUpdateProfile_UsernameAlreadyExists() throws Exception {
        Appuser user = new Appuser("username", "encodedPassword", "1234567890", "test@example.com", "Finland");

        when(appuserService.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("newUsername")).thenReturn(true);

        mockMvc.perform(put("/mobile/updateProfile")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"newUsername\",\"password\":\"newPassword\",\"profilePicture\":\"newPp\",\"chatColor\":\"#FFFFFF\"}")
                .principal(() -> "test@example.com"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username already exists"));
    }

    
}
