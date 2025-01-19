package com.kaphack.smart_flow_builder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kaphack.smart_flow_builder.constant.GeneralConstants;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.entity.Message;
import com.kaphack.smart_flow_builder.util.StaticContextAccessor;
import com.kaphack.smart_flow_builder.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class SmartFlowService implements ISmartFlowService {

  private final ModelService modelService;
  private final MessageService messageService;

  public ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) throws JsonProcessingException {
    var service = StringUtils.isNullOrEmpty(reqDto.getModel()) ? OpenAIFlowService.class : GeneralConstants.AVAILABLE_MODELS.get(reqDto.getModel());
    if (service == null) {
      return ResponseEntity.badRequest().body("Model not found");
    }
    return StaticContextAccessor.getBean(service)
        .getSmartFlow(reqDto);
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


//      List<Message> messagesBySessionId = new ArrayList<>();
//    if (StringUtils.isNullOrEmpty(sessionId)) {
//      sessionId = UUID.randomUUID().toString();
//      Message _message = new Message();
//      _message.setSessionId(sessionId);
//      _message.setMessage("Well, what do you need built?");
//      _message.setRole(Message.Role.system);
//      messageService.saveMessage(_message);
//      messagesBySessionId.add(_message);
//    } else {
//      messagesBySessionId = messageService.getMessagesBySessionId(sessionId);
//      if (messagesBySessionId != null && !messagesBySessionId.isEmpty()) {
//        List<OllamaChatMessageDto> mappedMessages = messagesBySessionId.stream()
//            .map(message -> new OllamaChatMessageDto(message.getRole().toString(), message.getMessage()))
//            .collect(Collectors.toList());
//        ollamaMessageList.addAll(mappedMessages);
//      }
//    }
//
//    ollamaMessageList.add(new OllamaChatMessageDto("user", reqDto.getPromptText()));
//
//    var outputConverter = new BeanOutputConverter<>(ModelOutputFormat.class);
//    String jsonSchema = outputConverter.getJsonSchema();
//    HashMap<?, ?> modelOutputFormat = objectMapper.readValue(jsonSchema, HashMap.class);
//    OllamaChatRequestDto ollamaChatRequestDto = OllamaChatRequestDto.builder()
//        .model(GeneralConstants.MODEL_NAME)
//        .format(modelOutputFormat)
////        .tools(getFunctionDefinition())
//        .options(OllamaOptions.builder().withTemperature(1.0).build())
//        .build();
//    ollamaChatRequestDto.setMessages(ollamaMessageList);
//
//    OllamaChatResponseDto ollamaChatResponseDto = modelService.ollamaChat(ollamaChatRequestDto);
//    ModelOutputFormat responseFromLLM = objectMapper.readValue(ollamaChatResponseDto.getMessage().getContent(), ModelOutputFormat.class);
//
//    Message message1 = new Message();
//    message1.setSessionId(sessionId);
//    message1.setMessage(reqDto.getPromptText());
//    message1.setRole(Message.Role.user);
//    messageService.saveMessage(message1);
//
//    Message message = new Message();
//    message.setSessionId(sessionId);
//    message.setMessage(objectMapper.writeValueAsString(responseFromLLM));
//    message.setRole(Message.Role.assistant);
//    messageService.saveMessage(message);

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
