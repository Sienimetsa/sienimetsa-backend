package sienimetsa.sienimetsa_backend.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import sienimetsa.sienimetsa_backend.domain.Message;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @MessageMapping("/send") //endpoint where messages are sent
    @SendTo("/topic/publicChat") //sends it to everyone in the group
    public Message sendMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return message;
    }
}

