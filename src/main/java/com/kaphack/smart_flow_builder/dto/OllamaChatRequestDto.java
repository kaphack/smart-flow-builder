package com.kaphack.smart_flow_builder.dto;

import com.kaphack.smart_flow_builder.constant.GeneralConstants;
import com.kaphack.smart_flow_builder.record.ModelOutputFormat;

import java.util.List;

public class OllamaChatRequestDto {
  private String model = GeneralConstants.MODEL_NAME;
  private List<Message> messages;
  private boolean stream = false;
  private ModelOutputFormat format;
  private List<Object> tools;

  class Message {
    private String role;
    private String content;
  }
}
