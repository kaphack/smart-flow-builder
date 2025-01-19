package com.kaphack.smart_flow_builder.service;

import com.kaphack.smart_flow_builder.entity.Message;
import com.kaphack.smart_flow_builder.repository.MessageRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

  public List<org.springframework.ai.chat.messages.Message> getPastConversation(String sessionId) {
    List<org.springframework.ai.chat.messages.Message> conversation = new ArrayList<>();
    List<com.kaphack.smart_flow_builder.entity.Message> messages = messageRepository.findBySessionId(sessionId);
    for (com.kaphack.smart_flow_builder.entity.Message message : messages) {
      if (message.getRole() == MessageType.SYSTEM) {
        conversation.add(new SystemMessage(message.getMessage()));
      } else if (message.getRole() == MessageType.USER) {
        conversation.add(new UserMessage(message.getMessage()));
      } else if (message.getRole() == MessageType.ASSISTANT) {
        conversation.add(new AssistantMessage(message.getMessage()));
      } else if (message.getRole() == MessageType.TOOL) {
        conversation.add(new AssistantMessage(message.getMessage()));
      }
    }
    return conversation;
  }

}
