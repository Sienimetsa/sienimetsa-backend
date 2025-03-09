package sienimetsa.sienimetsa_backend.web;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;

@RestController
@RequestMapping("/api/findings")
public class EntityController {

    @Autowired
    private AppuserRepository urepository;
    @Autowired
    private FindingRepository frepository;
    @Autowired
    private MushroomRepository mrepository;

    // Get all findings by user id
    //toimii
    @GetMapping("/userfindings/{id}")
    public List<Finding> getFindingsByUserId(@PathVariable("id") Long u_id) {
        Appuser user = urepository.findById(u_id).orElse(null);
        if (user != null) {
            return frepository.findByAppuser(user);
        }
        return List.of();
    }

    // Get all mushrooms
    //toimii
    @GetMapping("/allmushrooms")
    public List<Mushroom> getAllMushrooms() {
        return (List<Mushroom>) mrepository.findAll();
    }

    // Get all findings
    //toimii
    @GetMapping("/findings")
    public List<Finding> getAllFindings() {
        return (List<Finding>) frepository.findAll();
    }

    // Get a finding by id
    //toimii
    @GetMapping("/{id}")
    public ResponseEntity<Finding> getFindingById(@PathVariable Long id) {
        return frepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Creates a new finding
    
    @PostMapping("/")
    public ResponseEntity<Finding> createFinding(@RequestBody @Valid Finding finding) {
        if (finding.getCity() == null || finding.getCity().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Finding savedFinding = frepository.save(finding);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFinding);
    }

    // Edit a finding by id
    @PutMapping("/{id}")
    public ResponseEntity<Finding> updateFinding(@PathVariable("id") Long id, @RequestBody @Valid Finding updatedFinding) {
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinding(@PathVariable Long id) {
        if (frepository.existsById(id)) {
            frepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
