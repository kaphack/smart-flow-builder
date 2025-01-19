package com.kaphack.smart_flow_builder.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SmartFlowRequestDto {
  private String model;
  private String sessionId;
  @NotNull
  private String promptText;
  private String promptImage;
  private String inputFlowJson; // JSONObject
}
