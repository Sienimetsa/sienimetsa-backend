package sienimetsa.sienimetsa_backend.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
   
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
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
    public String users() {
        return "users";
    }
    @GetMapping("/mushrooms")
    public String mushrooms() {
        return "mushrooms";
    }
}
