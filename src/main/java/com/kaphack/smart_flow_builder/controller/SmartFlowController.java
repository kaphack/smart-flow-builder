package com.kaphack.smart_flow_builder.controller;

import com.kaphack.smart_flow_builder.record.SmartRequest;
import com.kaphack.smart_flow_builder.record.SmartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SmartFlowController {

  // todo:
  // voice prompt, chat based prompt, select steps and send particular prompt, image prompt

  // model output format:
  // status: processed, needDetails
  // response: flowJson || replyQuestion

  @GetMapping
  public ResponseEntity<SmartResponse> getSmartFlow(@RequestParam String sessionId, @RequestParam String promptText) {
    return null;
  }

  @PostMapping
  public ResponseEntity<SmartResponse> getSmartFlow(@RequestBody SmartRequest smartRequest) {
    return null;
  }

}
