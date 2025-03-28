package sienimetsa.sienimetsa_backend;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.dto.MobileLoginRequestDTO;
import sienimetsa.sienimetsa_backend.dto.MobileSignupRequestDTO;
import sienimetsa.sienimetsa_backend.jwt.JwtUtil;
import sienimetsa.sienimetsa_backend.web.AppuserSignUpServiceImpl;
import sienimetsa.sienimetsa_backend.web.AuthController;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AppuserSignUpServiceImpl appuserSignUpService;

    @Mock
    private AppuserRepository appuserRepository;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testLogin_Success() throws Exception {
        MobileLoginRequestDTO loginRequest = new MobileLoginRequestDTO();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        Appuser appuser = new Appuser();
        appuser.setU_id(1L);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(new UsernamePasswordAuthenticationToken("test@example.com", "password"));

        when(appuserRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(appuser));
        when(jwtUtil.generateToken(loginRequest.getEmail(), appuser.getU_id())).thenReturn("mockedToken");

        mockMvc.perform(post("/mobile/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockedToken"));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(appuserRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(jwtUtil, times(1)).generateToken(loginRequest.getEmail(), appuser.getU_id());
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        MobileLoginRequestDTO loginRequest = new MobileLoginRequestDTO();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/mobile/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Authentication failed: Invalid credentials"));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testLogin_InternalServerError() throws Exception {
        MobileLoginRequestDTO loginRequest = new MobileLoginRequestDTO();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(post("/mobile/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred during authentication"));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testSignup_Success() throws Exception {
        MobileSignupRequestDTO signupRequest = new MobileSignupRequestDTO();
        signupRequest.setUsername("username1");
        signupRequest.setPassword("password123");
        signupRequest.setPhone("1234567890");
        signupRequest.setEmail("newuser@example.com");
        signupRequest.setCountry("Finland");

        when(appuserSignUpService.registerNewUser(any(MobileSignupRequestDTO.class)))
            .thenReturn("User registered successfully");

        mockMvc.perform(post("/mobile/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully"));

        verify(appuserSignUpService, times(1)).registerNewUser(any(MobileSignupRequestDTO.class));
    }
}