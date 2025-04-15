package sienimetsa.sienimetsa_backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sienimetsa.sienimetsa_backend.domain.Message;
import sienimetsa.sienimetsa_backend.domain.MessageRepository;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MessageRepository repository;
    //user can see old messages/chat history
    public List<Message> getChatHistory() {
        return repository.findAllByOrderByTimestampAsc();
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
