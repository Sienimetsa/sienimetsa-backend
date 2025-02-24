package sienimetsa.sienimetsa_backend.web;

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.dto.MobileProfileUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/profile")
public class AppUserProfileController {
    private static final Logger logger = LoggerFactory.getLogger(AppUserProfileController.class);
    @Autowired
    private AppuserRepository appuserRepository;

    @Autowired
    private FindingRepository findingRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, String> requestBody, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        if (userDetails == null) {
            logger.warn("Unauthorized access attempt to delete user");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    
        String email = requestBody.get("email"); // Get email from the request body
        Optional<Appuser> optionalAppuser = appuserRepository.findByEmail(email);
    
        if (!optionalAppuser.isPresent()) {
            logger.warn("User not found with email: {}", email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    
        Appuser appuser = optionalAppuser.get();
    
        // Delete all findings associated with this user
        findingRepository.deleteByAppuser(appuser);
        logger.info("Deleted findings associated with user: {}", email);
    
        // Delete the user
        appuserRepository.delete(appuser);
        logger.info("Deleted user with email: {}", email);
    
        // Invalidate the session after deletion
        request.getSession().invalidate();
        logger.info("Invalidated session for user: {}", email);
    
        return ResponseEntity.ok("User and associated data deleted successfully");
    }
    
    
    

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails, 
                                             @RequestBody MobileProfileUpdateDTO updateDTO) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Optional<Appuser> optionalAppuser = appuserRepository.findByEmail(userDetails.getUsername());
        if (!optionalAppuser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Appuser appuser = optionalAppuser.get();

        if (updateDTO.getUsername() != null && !updateDTO.getUsername().isEmpty()) {
            appuser.setUsername(updateDTO.getUsername());
        }
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updateDTO.getPassword());
            appuser.setPasswordHash(encodedPassword);
        }
        if (updateDTO.getChatColor() != null && !updateDTO.getChatColor().isEmpty()) {
            appuser.setChatColor(updateDTO.getChatColor());
        }
        if (updateDTO.getProfilePicture() != null && !updateDTO.getProfilePicture().isEmpty()) {
            appuser.setProfilePicture(updateDTO.getProfilePicture());
        }

        appuserRepository.save(appuser);
        return ResponseEntity.ok("Profile updated successfully");
    }

    @GetMapping
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Optional<Appuser> optionalAppuser = appuserRepository.findByEmail(userDetails.getUsername());
        if (!optionalAppuser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Appuser appuser = optionalAppuser.get();
        return ResponseEntity.ok(appuser);
    }
}