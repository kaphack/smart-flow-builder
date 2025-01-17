package com.kaphack.smart_flow_builder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaphack.smart_flow_builder.constant.GeneralConstants;
import com.kaphack.smart_flow_builder.dto.OllamaChatMessageDto;
import com.kaphack.smart_flow_builder.dto.OllamaChatRequestDto;
import com.kaphack.smart_flow_builder.dto.OllamaChatResponseDto;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.entity.Message;
import com.kaphack.smart_flow_builder.record.ModelOutputFormat;
import com.kaphack.smart_flow_builder.record.SmartResponse;
import com.kaphack.smart_flow_builder.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class SmartFlowService {

  private final OllamaChatModel chatModel;
  private final ObjectMapper    objectMapper;
  private final ModelService    modelService;
  private final MessageService  messageService;



  private static final List<Class<?>> functionBeanList = List.of(
      MockWeatherService.class
  );

  public ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) throws JsonProcessingException {
    String sessionId = reqDto.getSessionId();
    List<OllamaChatMessageDto> ollamaMessageList = new ArrayList<>(); // Mutable list
    if (StringUtils.isNullOrEmpty(sessionId)) {
      sessionId = UUID.randomUUID().toString();
    } else {
      List<Message> messagesBySessionId = messageService.getMessagesBySessionId(sessionId);
      if (messagesBySessionId != null && !messagesBySessionId.isEmpty()) { // Fix null check before empty
        List<OllamaChatMessageDto> mappedMessages = messagesBySessionId.stream()
            .map(message -> new OllamaChatMessageDto(message.getRole().toString(), message.getMessage()))
            .collect(Collectors.toList());
        ollamaMessageList.addAll(mappedMessages);
      }
    }

    ollamaMessageList.add(new OllamaChatMessageDto("user", reqDto.getPromptText()));

    var outputConverter = new BeanOutputConverter<>(ModelOutputFormat.class);
    String jsonSchema = outputConverter.getJsonSchema();
    HashMap<?, ?> modelOutputFormat = objectMapper.readValue(jsonSchema, HashMap.class);
    OllamaChatRequestDto ollamaChatRequestDto = OllamaChatRequestDto.builder()
        .model(GeneralConstants.MODEL_NAME)
        .format(modelOutputFormat)
        .tools(getFunctionDefinition())
        .prompt(reqDto.getPromptText())
        .build();
    ollamaChatRequestDto.setMessages(ollamaMessageList);

    OllamaChatResponseDto ollamaChatResponseDto = modelService.ollamaChat(ollamaChatRequestDto);
    ModelOutputFormat responseFromLLM = objectMapper.readValue(ollamaChatResponseDto.getMessage().getContent(), ModelOutputFormat.class);

    Message message = new Message();
    message.setSessionId(sessionId);
    message.setMessage(responseFromLLM.message());
    message.setRole(Message.Role.USER);
    messageService.saveMessage(message);

    ModelOutputFormat updatedResponseFromLLM = new ModelOutputFormat(
        responseFromLLM.status(),
        responseFromLLM.flowJson(),
        responseFromLLM.message(),
        sessionId
    );
    return ResponseEntity.ok(updatedResponseFromLLM);
  }

  private List<Object> getFunctionDefinition() {
    List<Object> functionDefinition = new ArrayList<>();
    functionBeanList.forEach(
        bean -> {
          try {
            var outputConverter = new BeanOutputConverter<>(bean);
            Map<?, ?> map = objectMapper.readValue(outputConverter.getJsonSchema(), Map.class);
            functionDefinition.add(map);
          } catch (Exception e) {
            log.error("Error in getFunctionDefinition() foreach");
          }
        }
    );
    return functionDefinition;
  }

//  var outputConverter = new BeanOutputConverter<>(ModelOutputFormat.class);
//    System.out.println(outputConverter.getJsonSchema());
//  ChatOptions options = OllamaOptions.builder()
//      .withFormat(outputConverter.getJsonSchema())
//      .build();
//  Prompt prompt = new Prompt(reqDto.getPromptText(), options);
//  String output = chatModel.call(prompt).getResult().getOutput().getContent();
//
//    return ResponseEntity.ok(
//        new SmartResponse(output)
//    );
}
