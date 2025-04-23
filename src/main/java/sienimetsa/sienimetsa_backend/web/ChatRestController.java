package sienimetsa.sienimetsa_backend.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sienimetsa.sienimetsa_backend.dto.MessageDTO;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatRestController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/history")
    public List<MessageDTO> getChatHistory() {
        return chatService.getChatHistory();
    }
}
