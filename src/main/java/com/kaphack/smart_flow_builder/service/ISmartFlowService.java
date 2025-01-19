package com.kaphack.smart_flow_builder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import org.springframework.http.ResponseEntity;

public interface ISmartFlowService {

  ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) throws JsonProcessingException;

}
