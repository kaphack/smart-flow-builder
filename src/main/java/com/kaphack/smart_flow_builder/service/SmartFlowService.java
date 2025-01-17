package com.kaphack.smart_flow_builder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaphack.smart_flow_builder.constant.GeneralConstants;
import com.kaphack.smart_flow_builder.dto.OllamaChatRequestDto;
import com.kaphack.smart_flow_builder.dto.OllamaChatResponseDto;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.record.ModelOutputFormat;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class SmartFlowService {

  private final OllamaChatModel chatModel;
  private final ObjectMapper    objectMapper;
  private final ModelService    modelService;


  private static final List<Class<?>> functionBeanList = List.of(
      MockWeatherService.class
  );

  public ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) throws JsonProcessingException {
    var outputConverter = new BeanOutputConverter<>(ModelOutputFormat.class);
//        OllamaChatResponseDto responseDto = new OllamaChatResponseDto();
//        OllamaChatResponseDto.Message message = responseDto.new Message();
//        message.setRole("user");
//        message.setContent(reqDto.getPromptText());
    List<OllamaChatRequestDto.Message> listOfMessages = new ArrayList<>();
    HashMap modelOutputFormat = objectMapper.convertValue(reqDto, HashMap.class);
    OllamaChatRequestDto ollamaChatRequestDto = OllamaChatRequestDto.builder().model(GeneralConstants.MODEL_NAME).format(modelOutputFormat).messages(listOfMessages) //
        .tools(getFunctionDefinition())
        .build();
    OllamaChatResponseDto ollamaChatResponseDto = modelService.ollamaChat(ollamaChatRequestDto);
    ModelOutputFormat responseFromLLM = objectMapper.readValue(ollamaChatResponseDto.getMessage().getContent(), ModelOutputFormat.class);
    return ResponseEntity.ok(responseFromLLM);
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
