package com.kaphack.smart_flow_builder.dto;

import com.kaphack.smart_flow_builder.constant.GeneralConstants;
import lombok.Builder;
import lombok.Data;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;

import java.util.HashMap;
import java.util.List;

@Builder
@Data
@Deprecated
public class OllamaChatRequestDto {
  private String                           model  = GeneralConstants.MODEL_NAME;
  private List<OllamaChatMessageDto>       messages;
  private boolean                          stream = false;
  private HashMap<?, ?>                    format;
  private List<OllamaApi.ChatRequest.Tool> tools;
  private OllamaOptions                    options;
}
