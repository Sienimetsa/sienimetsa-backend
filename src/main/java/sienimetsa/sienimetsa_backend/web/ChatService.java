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
    //saves messages
    public Message saveMessage(Message message) {
        return repository.save(message);
    }
}
