package com.kaphack.smart_flow_builder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class OllamaChatMessageDto {
  private String role;
  private String content;
}
