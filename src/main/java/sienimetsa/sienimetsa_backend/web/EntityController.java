package sienimetsa.sienimetsa_backend.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;

@RestController
@RequestMapping("/findings")
public class EntityController {

    @Autowired
    private AppuserRepository urepository;
    @Autowired
    private FindingRepository frepository;
    @Autowired
    private MushroomRepository mrepository;

    @GetMapping("/userfindings/{id}")
    @ResponseBody
    public List<?> findings(@PathVariable("id") Long u_id) {
        Appuser user = urepository.findById(u_id).orElse(null);
        if (user != null) {
            return frepository.findByAppuser(user);
        }
        return List.of(); // Return empty list if user not found
    }

    // all findings
    @GetMapping("/allmushrooms")
    @ResponseBody
    public List<?> getAllMushrooms() {
        return (List<Mushroom>) mrepository.findAll();
    }

    // adds a finding
    @PostMapping("/addfinding")
    public ResponseEntity<Finding> addFinding(@RequestBody @Valid Finding finding) {
        Finding savedFinding = frepository.save(finding);
        return ResponseEntity.ok(savedFinding);
    }

    // Updates finding using it's id
    @PutMapping("/updatefinding/{id}")
    public ResponseEntity<Finding> updateFinding(@PathVariable Long id, @RequestBody Finding updatedFinding) {
        return frepository.findById(id).map(finding -> {
            finding.setMushroom(updatedFinding.getMushroom());
            finding.setCity(updatedFinding.getCity());
            finding.setF_time(updatedFinding.getF_time());
            finding.setNotes(updatedFinding.getNotes());
            return ResponseEntity.ok(frepository.save(finding));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletes a findng based on it's id
    @DeleteMapping("/deletefinding/{id}")
    public ResponseEntity<Void> deleteFinding(@PathVariable Long id) {
        if (frepository.existsById(id)) {
            frepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
