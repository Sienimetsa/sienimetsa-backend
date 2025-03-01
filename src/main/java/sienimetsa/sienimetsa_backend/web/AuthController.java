package sienimetsa.sienimetsa_backend.web;

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.dto.MobileLoginRequestDTO;
import sienimetsa.sienimetsa_backend.dto.MobileSignupRequestDTO;
import sienimetsa.sienimetsa_backend.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;

@RestController
public class AuthController {

    @Autowired
    private AppuserSignUpServiceImpl appuserSignUpService; // Handles user registration logic

    @Autowired
    private JwtUtil jwtUtil; // used for generating tokens

    @Autowired
    private AuthenticationManager authenticationManager; // Manages authentication

    @Autowired
    private AppuserRepository appuserRepository; // Repository to access Appuser data

/*  Handles user login for mobile users. 
  - Authenticates user credentials. 
  - Generates and returns a JWT token upon successful authentication. 
  - authRequest Contains user email and password. 
    - ResponseEntity containing JWT token or error message. */
    @PostMapping("/mobile/login")
    public ResponseEntity<?> login(@RequestBody MobileLoginRequestDTO authRequest) {
        try {
            // Authenticate user credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            // Retrieve the u_id from the database
            Appuser appuser = appuserRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Long u_id = appuser.getU_id();

            // Generate JWT token
            String token = jwtUtil.generateToken(authRequest.getEmail(), u_id);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {

            e.printStackTrace();
            return ResponseEntity.status(401).body("Authentication failed: Invalid credentials");
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred during authentication");
        }
    }

    /**
     * Handles new user registration for mobile users.
     * - Calls the service to create a new user.
     * - Returns a success message if registration is successful.
     * - signupRequest Contains user registration details.
     * - ResponseEntity with a success message and HTTP status.
     */
    @PostMapping("/mobile/signup")
    public ResponseEntity<?> signup(@RequestBody MobileSignupRequestDTO signupRequest) {
        String message = appuserSignUpService.registerNewUser(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    /**
     * Inner class representing the authentication response containing the JWT token.
     */
    public static class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}