package com.kaphack.smart_flow_builder.dto;

import lombok.Data;

@Data
public class OllamaChatResponseDto {
  private String  model;
  private Object  created_at;
  private Message message;
  private String  done_reason;
  private Boolean done;
  private Long    total_duration;
  private Long    load_duration;
  private Integer prompt_eval_count;
  private Long    prompt_eval_duration;
  private Integer eval_count;
  private Long    eval_duration;

  @Data
  class Message {
    private String role;
    private String content;
  }
}
