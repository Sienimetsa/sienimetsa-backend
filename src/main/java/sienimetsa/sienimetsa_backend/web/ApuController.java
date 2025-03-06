package sienimetsa.sienimetsa_backend.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;
import sienimetsa.sienimetsa_backend.domain.User;
import sienimetsa.sienimetsa_backend.domain.UserRepository;

@Controller
@RequestMapping("/apu")
public class ApuController {

    @Autowired
    private AppuserRepository urepository;
    @Autowired
    private FindingRepository frepository;

    @Autowired
    private MushroomRepository mrepository;

    @Autowired
    private UserRepository repository;

    @GetMapping("/allusers")
    public List<User> getUsers() {
        return (List<User>) repository.findAll();
    }

    @GetMapping("/allappusers")
    public List<Appuser> getAppUsers() {
        return (List<Appuser>) urepository.findAll();
    }

    @GetMapping("/allmushrooms")
    public List<Mushroom> getMushrooms() {
        return (List<Mushroom>) mrepository.findAll();
    }

    @GetMapping("/allfindings")
    public List<Finding> getFindings() {
        return (List<Finding>) frepository.findAll();
    }

    @GetMapping("/allprofiles")
    public String getProfile() {
        return "profile";
    }
}
