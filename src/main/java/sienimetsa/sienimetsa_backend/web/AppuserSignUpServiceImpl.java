package sienimetsa.sienimetsa_backend.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.dto.MobileSignupRequestDTO;

@Service
public class AppuserSignUpServiceImpl {

    private final AppuserRepository appuserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor to inject AppuserRepository and BCryptPasswordEncoder
    public AppuserSignUpServiceImpl(AppuserRepository appuserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.appuserRepository = appuserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to register a new user
     public String registerNewUser(MobileSignupRequestDTO signupRequestDTO ) {
        if (signupRequestDTO.isDryRun()) {
            // Only validate input and return a success message without creating the user
            if (appuserRepository.existsByUsername(signupRequestDTO.getUsername())) {
                throw new IllegalArgumentException("Username is already taken!");
            }
    
            if (appuserRepository.existsByEmail(signupRequestDTO.getEmail())) {
                throw new IllegalArgumentException("Email is already taken!");
            }
    
            return "Validation passed";  // Return validation message
        }
        
        // Actual user creation logic if dryRun is false
        if (appuserRepository.existsByUsername(signupRequestDTO.getUsername())) {
            throw new IllegalArgumentException("Username is already taken!");
        }
    
        if (appuserRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already taken!");
        }
    

        // Create new Appuser object from DTO
        Appuser newUser = new Appuser();
        newUser.setUsername(signupRequestDTO.getUsername());
        newUser.setEmail(signupRequestDTO.getEmail());
        newUser.setPhone(signupRequestDTO.getPhone());
        newUser.setCountry(signupRequestDTO.getCountry());
        newUser.setPasswordHash(passwordEncoder.encode(signupRequestDTO.getPassword())); // Encrypt password
        newUser.setChatColor("#574E47"); // Set default chat color
        newUser.setProfilePicture("pp1"); // Set default profile picture
        newUser.setLevel(1);

        // Save user to the database
        appuserRepository.save(newUser);

        return "User registered successfully!";
    }
}
