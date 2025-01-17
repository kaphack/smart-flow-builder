package com.kaphack.smart_flow_builder.service;

import com.kaphack.smart_flow_builder.entity.Message;
import com.kaphack.smart_flow_builder.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

  private final MessageRepository messageRepository;

  @Autowired
  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public Message saveMessage(Message message) {
    return messageRepository.save(message);
  }

  public List<Message> getMessagesBySessionId(String sessionId) {
    return messageRepository.findBySessionId(sessionId);
  }
}
