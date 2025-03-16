package sienimetsa.sienimetsa_backend.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.validation.Valid;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;
import sienimetsa.sienimetsa_backend.service.AwsUploadService;

@RestController
@RequestMapping("/apu")
public class EntityController {

    @Autowired
    private AppuserRepository urepository;
    @Autowired
    private FindingRepository frepository;
    @Autowired
    private AwsUploadService awsUploadService;
    @Autowired
    private MushroomRepository mrepository;

    // Get all findings by user id
    @GetMapping("/userfindings/{id}")
    public List<Finding> getFindingsByUserId(@PathVariable("id") Long u_id) {
        Appuser user = urepository.findById(u_id).orElse(null);
        if (user != null) {
            return frepository.findByAppuser(user);
        }
        return List.of();
    }

    // Get a finding by id
    @GetMapping("/finding/{id}")
    public ResponseEntity<Finding> getFindingById(@PathVariable Long id) {
        return frepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Creates a new finding with image upload from React Native
    @PostMapping(value = "/newfinding", consumes = { "multipart/form-data" })
    public ResponseEntity<Finding> createFinding(@RequestPart("file") MultipartFile imageFile, @RequestPart("finding") String findingJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Add this to handle LocalDateTime
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Optional: Format dates as ISO strings

        Finding finding;
        try {
            finding = mapper.readValue(findingJson, Finding.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = awsUploadService.uploadImage(imageFile);
                finding.setImageURL(imageUrl);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        Optional<Appuser> appuserOptional = urepository.findById(finding.getAppuser().getU_id());
        Optional<Mushroom> mushroomOptional = mrepository.findById(finding.getMushroom().getM_id());

        if (appuserOptional.isPresent() && mushroomOptional.isPresent()) {
            finding.setAppuser(appuserOptional.get());
            finding.setMushroom(mushroomOptional.get());
            Finding savedFinding = frepository.save(finding);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFinding);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Edit a finding by id
    @PutMapping("/editfinding/{id}")
    public ResponseEntity<Finding> updateFinding(@PathVariable("id") Long id,
            @RequestBody @Valid Finding updatedFinding) {
        Optional<Finding> existingFindingOptional = frepository.findById(id);

        if (existingFindingOptional.isPresent()) {
            Finding existingFinding = existingFindingOptional.get();
            existingFinding.setCity(updatedFinding.getCity());
            existingFinding.setNotes(updatedFinding.getNotes());
            existingFinding.setAppuser(updatedFinding.getAppuser());
            existingFinding.setMushroom(updatedFinding.getMushroom());
            existingFinding.setF_time(updatedFinding.getF_time());

            Finding savedFinding = frepository.save(existingFinding);
            return ResponseEntity.ok(savedFinding);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a finding by id
    @DeleteMapping("/deletefinding/{id}")
    public ResponseEntity<Void> deleteFinding(@PathVariable Long id) {
        if (frepository.existsById(id)) {
            frepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}