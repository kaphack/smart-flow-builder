package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FlowNode(
    @JsonProperty(required = true, value = "id") String id,
    @JsonProperty(required = true, value = "type") String type,
    @JsonProperty(required = true, value = "data") Data data
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
}
