package com.kaphack.smart_flow_builder.dto;

import com.kaphack.smart_flow_builder.constant.GeneralConstants;
import com.kaphack.smart_flow_builder.record.ModelOutputFormat;
import lombok.Builder;

import java.util.List;

@Builder
public class OllamaChatRequestDto {
  private String model = GeneralConstants.MODEL_NAME;
  private List<Message> messages;
  private boolean stream = false;
  private Object format;
  private List<Object> tools;

  public class Message {
    private String role;
    private String content;
  }
}
