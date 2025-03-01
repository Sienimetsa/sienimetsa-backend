package sienimetsa.sienimetsa_backend.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;

@RestController
public class EntityController{

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

    @GetMapping("/allmushrooms")
    @ResponseBody
    public List<?> getAllMushrooms() {
        return (List<Mushroom>) mrepository.findAll();
    }

    
}
