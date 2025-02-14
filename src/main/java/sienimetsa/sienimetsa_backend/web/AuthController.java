package sienimetsa.sienimetsa_backend.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserProfile;
import sienimetsa.sienimetsa_backend.domain.AppuserProfileRepository;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.dto.MobileLoginRequestDTO;
import sienimetsa.sienimetsa_backend.dto.MobileProfileUpdateDTO;
import sienimetsa.sienimetsa_backend.dto.MobileSignupRequestDTO;
import sienimetsa.sienimetsa_backend.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private AppuserRepository userRepository;

    @Autowired
    private AppuserProfileRepository profileRepository;

    @Autowired
    private AppuserService appuserService; 
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

  
    @PostMapping("/mobile/login")
    public ResponseEntity<?> login(@RequestBody MobileLoginRequestDTO authRequest) {
        Optional<Appuser> userOptional = appuserService.getUserByEmail(authRequest.getEmail());

        if (userOptional.isEmpty() || !passwordEncoder.matches(authRequest.getPassword(), userOptional.get().getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(authRequest.getEmail());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

  
    @PostMapping("/mobile/signup")
    public ResponseEntity<?> signup(@RequestBody MobileSignupRequestDTO signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email already in use"));
        }
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Username already exists"));
        }

        Appuser newUser = new Appuser(
            signupRequest.getUsername(),
            passwordEncoder.encode(signupRequest.getPassword()), 
            signupRequest.getPhone(),
            signupRequest.getEmail(),
            signupRequest.getCountry()
        );
        userRepository.save(newUser);

        AppuserProfile profile = new AppuserProfile(newUser, "#000000", "pp1");
        profileRepository.save(profile);

        String token = jwtUtil.generateToken(newUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("token", token));
    }

   
    @GetMapping("/mobile/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal Appuser user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "User not authenticated"));
        }

        Optional<Appuser> foundUser = appuserService.getUserByEmail(user.getEmail());
        if (foundUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "User not found"));
        }

        AppuserProfile profile = profileRepository.findByUser(foundUser.get())
                .orElse(new AppuserProfile(foundUser.get(), "#000000", "pp1"));

        return ResponseEntity.ok(Map.of(
            "username", foundUser.get().getUsername(),
            "email", foundUser.get().getEmail(),
            "profilePicture", profile.getProfilePicture(),
            "chatColor", profile.getChatColor()
        ));
    }

   
    @PutMapping("/mobile/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody MobileProfileUpdateDTO request, @AuthenticationPrincipal Appuser user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "User not authenticated"));
        }

        Optional<Appuser> foundUserOpt = appuserService.getUserByEmail(user.getEmail());
        if (foundUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "User not found"));
        }

        Appuser foundUser = foundUserOpt.get();

        if (!foundUser.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Username already exists"));
        }

        foundUser.setUsername(request.getUsername());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            foundUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(foundUser);

        AppuserProfile profile = profileRepository.findByUser(foundUser)
                .orElse(new AppuserProfile(foundUser, "#000000", "pp1"));
        profile.setProfilePicture(request.getProfilePicture());
        profile.setChatColor(request.getChatColor());
        profileRepository.save(profile);

        return ResponseEntity.ok(Collections.singletonMap("message", "Profile updated successfully"));
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
