package com.kaphack.smart_flow_builder.dto;

import com.kaphack.smart_flow_builder.constant.GeneralConstants;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class OllamaChatRequestDto {
  private String                     model  = GeneralConstants.MODEL_NAME;
  private List<OllamaChatMessageDto> messages;
  private boolean                    stream = false;
  private Object                     format;
  private List<Object>               tools;
}
