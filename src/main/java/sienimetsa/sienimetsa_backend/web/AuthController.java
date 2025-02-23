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
    private AppuserSignUpServiceImpl appuserSignUpService;

    @Autowired
    private JwtUtil jwtUtil; // used for generating tokens

    @Autowired
    private AppuserRepository appuserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/mobile/login")
    public ResponseEntity<?> login(@RequestBody MobileLoginRequestDTO authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            Appuser user = appuserRepository.findByEmail(authRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
                String token = jwtUtil.generateToken(authRequest.getEmail(), user);
    
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(401).body("Authentication failed: Invalid credentials");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred during authentication");
        }
    }

    @PostMapping("/mobile/signup")
    public ResponseEntity<?> signup(@RequestBody MobileSignupRequestDTO signupRequest) {
        String message = appuserSignUpService.registerNewUser(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

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