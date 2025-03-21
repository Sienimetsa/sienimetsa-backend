package sienimetsa.sienimetsa_backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sienimetsa.sienimetsa_backend.domain.Message;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")

public class ChatRestController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/history")
    public List<Message> getChatHistory() {
        return chatService.getChatHistory();
    }
}
