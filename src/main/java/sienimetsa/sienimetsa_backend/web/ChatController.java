package sienimetsa.sienimetsa_backend.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import sienimetsa.sienimetsa_backend.dto.MessageDTO;
import sienimetsa.sienimetsa_backend.domain.Message;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/send")
    @SendTo("/topic/publicChat")
    public MessageDTO sendMessage(MessageDTO messageDTO) {

        if (messageDTO.getTimestamp() == null) {
            messageDTO.setTimestamp(LocalDateTime.now());
        }
        if (messageDTO.getUsername() == null || messageDTO.getUsername().isEmpty()) {
            messageDTO.setUsername("Anonymous");
        }

        // Convert MessageDTO to Message entity
        Message message = new Message();
        message.setUsername(messageDTO.getUsername());
        message.setText(messageDTO.getText());
        message.setTimestamp(messageDTO.getTimestamp());

        // Save the message to the database
        chatService.saveMessage(message);

        // Return the DTO for broadcasting
        return messageDTO;
    }
}