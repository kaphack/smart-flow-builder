package com.kaphack.smart_flow_builder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kaphack.smart_flow_builder.constant.GeneralConstants;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.service.OllamaFlowServiceI;
import com.kaphack.smart_flow_builder.service.OpenAIFlowService;
import com.kaphack.smart_flow_builder.service.SmartFlowService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@CrossOrigin(origins = "*")
public class SmartFlowController {

  private final SmartFlowService smartFlowService;
  private final OllamaFlowServiceI ollamaFlowService;
  private final OpenAIFlowService openAIFlowService;

  // todo:
  // voice prompt, chat based prompt, select steps and send particular prompt, image prompt

  // model output format:
  // status: processed, needDetails
  // response: flowJson || replyQuestion

  @GetMapping("/messages")
  public ResponseEntity<?> getMessagesForSessionId(@RequestParam(required = true) String sessionId) {
    return smartFlowService.getAllMessages(sessionId);
  }

  @GetMapping("/models")
  public ResponseEntity<?> getModels() {
    return ResponseEntity.ok(GeneralConstants.AVAILABLE_MODELS.keySet());
  }

  @PostMapping
  public ResponseEntity<?> getSmartFlow(@Validated @RequestBody SmartFlowRequestDto reqDto) throws JsonProcessingException {
    return smartFlowService.getSmartFlow(reqDto);
  }

}
