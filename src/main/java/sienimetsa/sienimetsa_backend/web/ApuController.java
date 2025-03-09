package sienimetsa.sienimetsa_backend.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;
import sienimetsa.sienimetsa_backend.domain.User;
import sienimetsa.sienimetsa_backend.domain.UserRepository;

@RestController
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
    public List <User> getAllUsers() {
        return (List<User>) repository.findAll();
    }

    @GetMapping("/allappusers")
    public List<Appuser> getAllAppusers() {
        return (List<Appuser>) urepository.findAll();
    }

    @GetMapping("/allmushrooms")
    public List<Mushroom> getAllMushrooms() {
        return (List<Mushroom>) mrepository.findAll();
    }

    @GetMapping("/allfindings")
    public List <Finding> getAllFindings() {
        return (List<Finding>) frepository.findAll();
    }

    @GetMapping("/allprofiles")
    public String getProfile() {
        return "allprofiles";
    }
}
