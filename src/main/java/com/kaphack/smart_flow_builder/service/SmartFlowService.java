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
import com.kaphack.smart_flow_builder.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class SmartFlowService {

  private final OllamaChatModel chatModel;
  private final ObjectMapper    objectMapper;
  private final ModelService    modelService;
  private final MessageService  messageService;


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
    message.setMessage(objectMapper.writeValueAsString(responseFromLLM));
    message.setRole(Message.Role.assistant);
    messageService.saveMessage(message);

    ModelOutputFormat updatedResponseFromLLM = new ModelOutputFormat(
        responseFromLLM.status(),
        responseFromLLM.flowJson(),
        responseFromLLM.message(),
        sessionId
    );
    return ResponseEntity.ok(updatedResponseFromLLM);
  }

  private List<OllamaApi.ChatRequest.Tool> getFunctionDefinition() {
    return FunctionCallbackService.functionCallbackList.stream().map((functionCallback) -> {
      OllamaApi.ChatRequest.Tool.Function function = new OllamaApi.ChatRequest.Tool.Function(functionCallback.getName(), functionCallback.getDescription(), functionCallback.getInputTypeSchema());
      return new OllamaApi.ChatRequest.Tool(function);
    }).toList();
  }

  public ResponseEntity<Map<String, List<Message>>> getAllMessages(String sessionId) {
    List<Message> messages = messageService.getMessagesBySessionId(sessionId);
    if (messages == null || messages.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("messages", Collections.emptyList()));
    }
    Map<String, List<Message>> response = Map.of("messages", messages);
    return ResponseEntity.ok(response);
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
