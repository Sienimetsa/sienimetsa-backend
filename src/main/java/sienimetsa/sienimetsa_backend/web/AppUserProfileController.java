package sienimetsa.sienimetsa_backend.web;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @DeleteMapping("/delete/{u_id}")
    public ResponseEntity<?> deleteUser(@PathVariable("u_id") Long u_id, 
                                        @AuthenticationPrincipal UserDetails userDetails, 
                                        HttpServletRequest request) {
        // Check if user is authenticated
        if (userDetails == null) {
            logger.warn("Unauthorized access attempt to delete user with ID: {}", u_id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    
        // Fetch the user to delete
        Optional<Appuser> optionalAppuser = appuserRepository.findById(u_id);
        if (!optionalAppuser.isPresent()) {
            logger.warn("User not found with ID: {}", u_id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    
        // Delete all findings associated with this user
        findingRepository.deleteByAppuser(optionalAppuser.get());  // Deletes all findings associated with the user
        logger.info("Deleted findings associated with user ID: {}", u_id);
    
        // Delete the user
        appuserRepository.deleteById(u_id);
        logger.info("Deleted user with ID: {}", u_id);
    
        // Invalidate the session after the deletion
        request.getSession().invalidate();
        logger.info("Invalidated session for user ID: {}", u_id);
    
        return ResponseEntity.ok("User and associated findings deleted successfully");
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