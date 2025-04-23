package sienimetsa.sienimetsa_backend.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.Message;
import sienimetsa.sienimetsa_backend.domain.MessageRepository;
import sienimetsa.sienimetsa_backend.dto.MessageDTO;

@Service
public class ChatService {

    @Autowired
    private MessageRepository repository;

    @Autowired
    private AppuserRepository appuserRepository;

    // Fetches chat history with chatColor included
    public List<MessageDTO> getChatHistory() {
        List<Message> messages = repository.findAllByOrderByTimestampAsc();

        // Collect all unique usernames from messages
        List<String> usernames = messages.stream()
            .map(Message::getUsername)
            .distinct()
            .toList();

        // Fetch all Appusers in one query
        List<Appuser> users = appuserRepository.findByUsernameIn(usernames);

        // Map usernames to chatColor for quick lookup
        Map<String, String> userColorMap = users.stream()
            .collect(Collectors.toMap(Appuser::getUsername, Appuser::getChatColor));

        // Map each Message to a MessageDTO
        return messages.stream().map(message -> {
            MessageDTO dto = new MessageDTO();
            dto.setUsername(message.getUsername());
            dto.setText(message.getText());
            dto.setTimestamp(message.getTimestamp());
            dto.setChatColor(userColorMap.get(message.getUsername()));
            return dto;
        }).toList();
    }

    // Saves messages and deletes the oldest one if the total exceeds 100
    public Message saveMessage(Message message) {
        Message savedMessage = repository.save(message);

        // Check if the total number of messages exceeds 100
        long messageCount = repository.count();
        if (messageCount > 100) {
            // Find the oldest message and delete it
            Message oldestMessage = repository.findFirstByOrderByTimestampAsc();
            if (oldestMessage != null) {
                repository.delete(oldestMessage);
            }
        }

        return savedMessage;
    }
}
