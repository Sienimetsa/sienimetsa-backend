package sienimetsa.sienimetsa_backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getUsers(Model model) {
        model.addAttribute("user", new User());  // Empty user object
        model.addAttribute("users", repository.findAll()); // List of all users
        return "allusers";
    }

    @GetMapping("/allappusers")
    public String getAppUsers(Model model) {
        model.addAttribute("appuser", new Appuser());  // Empty user object
        model.addAttribute("appusers", urepository.findAll()); // List of all users
        return "allappusers";
    }

    @GetMapping("/allmushrooms")
    public String getMushrooms(Model model) {
        model.addAttribute("mushroom", new Mushroom());  // Empty mushroom object
        model.addAttribute("mushrooms", mrepository.findAll());
        return "allmushrooms"; // List of all mushrooms
    }

    @GetMapping("/allfindings")
    public String getFindings(Model model) {
        model.addAttribute("finding", new Finding());  // Empty finding object
        model.addAttribute("findings", frepository.findAll());
        return "allfindings"; // List of all findings
    }

    @GetMapping("/allprofiles")
    public String getProfile() {
        return "allprofiles";
    }
}
