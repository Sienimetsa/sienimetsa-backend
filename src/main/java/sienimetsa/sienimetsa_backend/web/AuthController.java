package sienimetsa.sienimetsa_backend.web;

import sienimetsa.sienimetsa_backend.dto.MobileLoginRequestDTO;
import sienimetsa.sienimetsa_backend.dto.MobileSignupRequestDTO;
import sienimetsa.sienimetsa_backend.jwt.JwtUtil;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AppuserRepository appuserRepository;  // Assuming you have a repository for Appuser
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // For encoding password

    // Existing login endpoint
    @PostMapping("/mobile/login")
    public ResponseEntity<?> login(@RequestBody MobileLoginRequestDTO authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            String token = jwtUtil.generateToken(authRequest.getEmail());

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Authentication failed: Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during authentication");
        }
    }

    // New signup endpoint for mobile registration
    @PostMapping("/mobile/signup")
    public ResponseEntity<?> signup(@RequestBody MobileSignupRequestDTO signupRequest) {
        try {
            // Check if the email already exists in the database
            if (appuserRepository.existsByEmail(signupRequest.getEmail())) {
                return ResponseEntity.status(400).body("Email already in use");
            }

            // Create a new Appuser entity and encode the password
            Appuser newUser = new Appuser();
            newUser.setEmail(signupRequest.getEmail());
            newUser.setUsername(signupRequest.getUsername());
            newUser.setPhone(signupRequest.getPhone());
            newUser.setCountry(signupRequest.getCountry());
            newUser.setPasswordHash(passwordEncoder.encode(signupRequest.getPassword()));

            // Save the new user to the database
            appuserRepository.save(newUser);

            // Return success message
            return ResponseEntity.status(201).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during registration");
        }
    }

    // DTO for the login response with the JWT token
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
