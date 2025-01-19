package com.kaphack.smart_flow_builder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.record.ModelOutputFormat;
import com.kaphack.smart_flow_builder.record.SmartResponse;
import com.kaphack.smart_flow_builder.repository.MessageRepository;
import com.kaphack.smart_flow_builder.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
public class OllamaFlowService implements ISmartFlowService {

  private final OllamaChatModel chatModel;
  private final ObjectMapper objectMapper;
  private final MessageRepository messageRepository;
  private final MessageService messageService;

  public ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) throws JsonProcessingException {
    List<Message> messageList = new ArrayList<>();
    String sessionId = reqDto.getSessionId();
    if (StringUtils.isNullOrEmpty(sessionId)) {
      sessionId = UUID.randomUUID().toString();
      var message = com.kaphack.smart_flow_builder.entity.Message.builder()
          .sessionId(sessionId)
          .message("Well, what do you need built?")
          .role(MessageType.SYSTEM)
          .build();
      messageRepository.save(message);
      messageList.add(new SystemMessage(message.getMessage()));
    } else {
      messageList = messageService.getPastConversation(sessionId);
    }
    messageList.add(new UserMessage(reqDto.getPromptText()));
    var beanOutputConverter = new BeanOutputConverter<>(ModelOutputFormat.class);
    OllamaOptions options = OllamaOptions.builder()
        .temperature(1.0)
        .model(reqDto.getModel())
        .format(beanOutputConverter.getJsonSchemaMap())
        .build();
    Prompt prompt = new Prompt(messageList, options);
    String output = chatModel.call(prompt).getResult().getOutput().getContent();
    messageRepository.save(
        com.kaphack.smart_flow_builder.entity.Message.builder()
            .sessionId(sessionId)
            .message(reqDto.getPromptText())
            .role(MessageType.USER)
            .build()
    );
    com.kaphack.smart_flow_builder.entity.Message assistantMessage = com.kaphack.smart_flow_builder.entity.Message.builder()
        .sessionId(sessionId)
        .message(output)
        .role(MessageType.ASSISTANT)
        .build();
    messageRepository.save(assistantMessage);
    return ResponseEntity.ok(new SmartResponse(sessionId, assistantMessage));
  }

}
