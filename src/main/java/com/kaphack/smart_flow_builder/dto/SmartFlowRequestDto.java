package com.kaphack.smart_flow_builder.dto;

import lombok.Data;

@Data
public class SmartFlowRequestDto {
  private String sessionId;
  private String promptText;
  private String promptImage;
  private String inputFlowJson; // JSONObject
}
