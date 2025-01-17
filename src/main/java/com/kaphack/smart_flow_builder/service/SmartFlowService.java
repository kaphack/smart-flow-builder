package com.kaphack.smart_flow_builder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaphack.smart_flow_builder.dto.OllamaChatRequestDto;
import com.kaphack.smart_flow_builder.dto.OllamaChatResponseDto;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.dto.SmartFlowResponseDto;
import com.kaphack.smart_flow_builder.record.ModelOutputFormat;
import com.kaphack.smart_flow_builder.record.SmartResponse;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
public class SmartFlowService {

  private final OllamaChatModel chatModel;
  private final ObjectMapper    objectMapper;
  private final ModelService    modelService;

  public ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) throws JsonProcessingException {
    OllamaChatRequestDto ollamaChatRequestDto = OllamaChatRequestDto.builder()
        .build();
    OllamaChatResponseDto ollamaChatResponseDto = modelService.ollamaChat(ollamaChatRequestDto);
    // todo extract
    ModelOutputFormat modelOutputFormat = null; // ollamaChatResponseDto.getMessage().getContent();
    return ResponseEntity.ok(modelOutputFormat);
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
