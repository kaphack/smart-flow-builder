package com.kaphack.smart_flow_builder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaphack.smart_flow_builder.constant.GeneralConstants;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.record.SmartResponse;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
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

  public ResponseEntity<SmartResponse> getSmartFlow(SmartFlowRequestDto reqDto) {
    ChatOptions options = OllamaOptions.builder()
        .withFormat(GeneralConstants.TEST_OUTPUT)
        .build();
    Prompt prompt = new Prompt(reqDto.getPromptText());
    String output = chatModel.call(prompt).getResult().getOutput().getContent();

    return ResponseEntity.ok(
        new SmartResponse(output)
    );

    //todo return ModelOutputDto by parsing output string
  }
}
