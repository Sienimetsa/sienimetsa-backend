package sienimetsa.sienimetsa_backend.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import sienimetsa.sienimetsa_backend.dto.MessageDTO;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @MessageMapping("/send")
    @SendTo("/topic/publicChat")
    public MessageDTO sendMessage(MessageDTO message) {

        // Message output to console
        System.out.println("Received message: " + message);
        System.out.println("Username: " + message.getUsername());
        System.out.println("Text: " + message.getText());
        
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }
    
        if (message.getUsername() == null || message.getUsername().isEmpty()) {
            message.setUsername("Anonymous");
        }
    
        return message;
    }
}
