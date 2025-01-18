package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.kaphack.smart_flow_builder.enums.FlowStepType;
import com.kaphack.smart_flow_builder.enums.MessageTypeEnum;

public record FlowNode(
    @JsonProperty(required = true, value = "id") @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonPropertyDescription("Unique identifier for the node. UUID")
    String id,
    @JsonProperty(required = true, value = "type")
    @JsonPropertyDescription("""
        Use SEND_MESSAGE_WIDGET to send a text message.
        Use SEND_WAIT_REPLY_MESSAGE_WIDGET to send a message to the customer and wait for their reply or asking for inputs.
        Use LIST_MESSAGE_WIDGET to present the customer with a list of quick reply options. The customer can select one of the provided options to proceed with the flow.
        Use API_REQUEST_WIDGET to make an API call.
        Use CUSTOM_ACTION_WIDGET to execute custom JavaScript actions after api requests to extract required data.
        Use LOGIC_WIDGET to evaluate conditions such as if, else if, and else for creating conditional flows.
        Use CONNECT_TO_AGENT_WIDGET to connect the user to a live agent.
        Use END_OF_FLOW_WIDGET to mark the completion of the flow.
        """)
    FlowStepType type,
    @JsonProperty(required = true, value = "data") Data data,
    @JsonProperty(required = true, value = "position") Position position
) {

  public record Data(
      @JsonProperty(required = true)
      @JsonPropertyDescription("Text message")
      String body,
      @JsonProperty(required = true, value = "transition")
      @JsonPropertyDescription("Represents the link or pathway between two nodes, defining the flow or interaction within the system.")
      Transition[] transition,
      @JsonPropertyDescription("The variable names that will have the values of the user's response for SEND_WAIT_REPLY_MESSAGE_WIDGET and results of other node types.")
      @JsonProperty(required = true, value = "variables") Variables variables,
      @JsonProperty(value = "message_type", required = true)
      @JsonPropertyDescription("Applicable only for node type LIST_MESSAGE_WIDGET, for other types, it should be set to null.")
      MessageTypeEnum message_type,
      @JsonProperty(value = "messageList")
      @JsonPropertyDescription("Applicable only for node type LIST_MESSAGE_WIDGET, for other types, it should be set to null.")
      MessageList[] messageList,
      @JsonPropertyDescription("JavaScript function code. Applicable only for node type CUSTOM_ACTION_WIDGET, for other types, it should be set to null.")
      String javaScriptFunction
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
