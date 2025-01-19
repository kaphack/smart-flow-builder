package com.kaphack.smart_flow_builder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.record.ModelOutputFormat;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.messages.Message;
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

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
public class OllamaFlowServiceI implements ISmartFlowService {

  private final OllamaChatModel chatModel;
  private final ObjectMapper objectMapper;

  public ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) throws JsonProcessingException {
    String sessionId = reqDto.getSessionId();
    List<Message> ollamaMessageList = new ArrayList<>();
    ollamaMessageList.add(new SystemMessage("Well, what do you need built?"));
    ollamaMessageList.add(new UserMessage(reqDto.getPromptText()));
    BeanOutputConverter beanOutputConverter = new BeanOutputConverter<>(ModelOutputFormat.class);
//    new PromptTemplate(beanOutputConverter.getFormat(), beanOutputConverter.getJsonSchema()).;
    OllamaOptions options = OllamaOptions.builder()
        .temperature(1.0)
        .model(reqDto.getModel())
        .format(beanOutputConverter.getJsonSchemaMap())
        .build();
    Prompt prompt = new Prompt(ollamaMessageList, options);
    String output = chatModel.call(prompt).getResult().getOutput().getContent();
    ModelOutputFormat responseFromLLM = objectMapper.readValue(output, ModelOutputFormat.class);

    ModelOutputFormat updatedResponseFromLLM = new ModelOutputFormat(
        responseFromLLM.status(),
        responseFromLLM.flowJson(),
//        responseFromLLM.reply(),
        sessionId
    );
    return ResponseEntity.ok(updatedResponseFromLLM);
  }

}
