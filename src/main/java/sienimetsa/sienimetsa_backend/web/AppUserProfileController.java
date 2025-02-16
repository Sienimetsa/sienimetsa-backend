package sienimetsa.sienimetsa_backend.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.dto.MobileProfileUpdateDTO;

@RestController
@RequestMapping("/api/profile")
public class AppUserProfileController {

    @Autowired
    private AppuserRepository appuserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails, 
                                             @RequestBody MobileProfileUpdateDTO updateDTO) {
        // Ensure the user is authenticated
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        // Using email from JWT as the username identifier
        Optional<Appuser> optionalAppuser = appuserRepository.findByEmail(userDetails.getUsername());
        if (!optionalAppuser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Appuser appuser = optionalAppuser.get();

        // Update username if provided
        if (updateDTO.getUsername() != null && !updateDTO.getUsername().isEmpty()) {
            appuser.setUsername(updateDTO.getUsername());
        }
        // Update password after encoding if provided
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updateDTO.getPassword());
            appuser.setPasswordHash(encodedPassword);
        }
        // Update chat color if provided
        if (updateDTO.getChatColor() != null && !updateDTO.getChatColor().isEmpty()) {
            appuser.setChatColor(updateDTO.getChatColor());
        }
        // Update profile picture if provided
        if (updateDTO.getProfilePicture() != null && !updateDTO.getProfilePicture().isEmpty()) {
            appuser.setProfilePicture(updateDTO.getProfilePicture());
        }

        appuserRepository.save(appuser);
        return ResponseEntity.ok("Profile updated successfully");
    }
}