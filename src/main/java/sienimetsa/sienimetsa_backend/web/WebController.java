package sienimetsa.sienimetsa_backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.MushroomRepository;


@Controller
public class WebController {

    @Autowired
    private AppuserRepository urepository;
    @Autowired
    private FindingRepository frepository;

    @Autowired
    private MushroomRepository mrepository;
   
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }
    @GetMapping("/")
    public String defaultpage() {
        return "redirect:/frontpage";
    }
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    @GetMapping("/frontpage")
    public String frontpage() {
        return "frontpage";
    }
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("appuser", new Appuser());  // Empty user object
        model.addAttribute("appusers", urepository.findAll()); // List of all users
        return "users";
    }
    @GetMapping("/editusers/{id}") //edit mushroom by id
    public String editusers(@PathVariable("id") Long u_id, Model model){
        model.addAttribute("appuser", urepository.findById(u_id));
        return "editusers";
    }

    @GetMapping("/deleteu/{id}")
    public String deleteAppuser(@PathVariable("id") Long u_id, Model model) {
        frepository.deleteById(u_id); // deletes appusers findings
        urepository.deleteById(u_id); // deletes appuser
        return "redirect:/users";
    }

    @PostMapping("/saveu")
    public String saveAppuser(@Valid @ModelAttribute Appuser appuser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("appusers", urepository.findAll()); // Send the appuser back in case of errors
            return "users"; // Return to the same form with errors
        }
        urepository.save(appuser);  // Save the user
        return "redirect:/users";  // Redirect to users page after saving
    }
      
    @GetMapping("/mushrooms")
    public String mushrooms(Model model) {
        model.addAttribute("mushroom", new Mushroom());  // Empty mushroom object
        model.addAttribute("mushrooms", mrepository.findAll()); // List of all mushrooms
        return "mushrooms";
    }
    @GetMapping("/edit/{id}") //edit mushroom by id
    public String editmushroom(@PathVariable("id") Long m_id, Model model){
        model.addAttribute("mushroom", mrepository.findById(m_id));
        return "editmushrooms";
    }

    @PostMapping("/savem")
    public String editMushroom(@Valid @ModelAttribute Mushroom mushroom, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("mushrooms", mrepository.findAll());
            return "mushrooms";
        }
        mrepository.save(mushroom);
        return "redirect:mushrooms";
    }

    @GetMapping("/findings")
    public String showFindings(Model model) {
        model.addAttribute("finding", new Finding());  // Empty finding object
        model.addAttribute("findings", frepository.findAll()); // List of all findings
        return "findings";
    }

    @GetMapping("/findings/edit/{id}")
    public String editFinding(@PathVariable("id") Long id, Model model) {
        Finding finding = frepository.findById(id).orElse(null);
        if (finding == null) {
            return "redirect:/findings";
        }
        model.addAttribute("finding", finding);
        return "editfindings";  
    }

    @PostMapping("/findings/edit/{id}")
    public String updateFinding(@PathVariable("id") Long id, @ModelAttribute Finding updatedFinding) {
        Finding existingFinding = frepository.findById(id).orElse(null);
        if (existingFinding != null) {
            existingFinding.setCity(updatedFinding.getCity());
            existingFinding.setNotes(updatedFinding.getNotes());
            frepository.save(existingFinding);
        }
        return "redirect:/findings";  // Korjattu reitti!
    }
}