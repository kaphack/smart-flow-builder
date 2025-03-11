package com.kaphack.smart_flow_builder.service;

import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import org.springframework.http.ResponseEntity;

public interface ISmartFlowService {

  ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) throws Exception;

  ResponseEntity<?> generateJavascript(SmartFlowRequestDto reqDto);
}
