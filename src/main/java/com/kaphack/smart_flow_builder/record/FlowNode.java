package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaphack.smart_flow_builder.enums.FlowStepType;

public record FlowNode(
    @JsonProperty(required = true, value = "id") @JsonFormat(shape = JsonFormat.Shape.STRING) String id,
    @JsonProperty(required = true, value = "type") FlowStepType type,
    @JsonProperty(required = true, value = "data") Data data,
    @JsonProperty(required = true, value = "position") Position position
) {

  public record Data(
      @JsonProperty(required = true, value = "body") String body,
      @JsonProperty(required = true, value = "transition") Transition[] transition,
      @JsonProperty(required = true, value = "variables") Variables variables,
      @JsonProperty(value = "message_type") String message_type,
      @JsonProperty(value = "messageList") MessageList[] messageList
  ) {

    public record Transition(
        String id,
        String label
    ) {
    }

    public record Variables(
        @JsonProperty(required = true, value = "reply_message") String reply_message
    ) {
    }

    public record MessageList(
        String value,
        String id,
        @JsonProperty(value = "transitionId") String transitionId
    ) {
    }
  }

  public record Position(
      @JsonProperty(required = true) int x,
      @JsonProperty(required = true) int y
  ) {
  }
}
