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
     public String registerNewUser(MobileSignupRequestDTO signupRequestDTO) {
        
        // Check if the email already exists
        if (appuserRepository.findByEmail(signupRequestDTO.getEmail()).isPresent()) {
            return "Email is already taken!";
        }
        // Check if the username already exists
        if (appuserRepository.findByUsername(signupRequestDTO.getUsername()).isPresent()) {
            return "Username is already taken!";
        }

        // Create new Appuser object from DTO
        Appuser newUser = new Appuser();
        newUser.setUsername(signupRequestDTO.getUsername());
        newUser.setEmail(signupRequestDTO.getEmail());
        newUser.setPhone(signupRequestDTO.getPhone());
        newUser.setCountry(signupRequestDTO.getCountry());
        newUser.setPasswordHash(passwordEncoder.encode(signupRequestDTO.getPassword())); // Encrypt password
        newUser.setChatColor("black"); // Set default chat color
        newUser.setProfilePicture("p1"); // Set default profile picture

        // Save user to the database
        appuserRepository.save(newUser);

        return "User registered successfully!";
    }
}
