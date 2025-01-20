package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.kaphack.smart_flow_builder.enums.FlowStepType;
import com.kaphack.smart_flow_builder.enums.MessageTypeEnum;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@JsonClassDescription("FlowNode represents a node in the flow. each node is the config for a step in the flow.")
public record FlowNode(
    @JsonProperty(required = true, value = "id") @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonPropertyDescription("Unique identifier for the node. Generate using function UUID.randomUUID().toString()")
    String id,
    @JsonProperty(required = true, value = "type")
    @JsonPropertyDescription("""
        Use SEND_MESSAGE_WIDGET to send a text message.
        Use SEND_WAIT_REPLY_MESSAGE_WIDGET to send a message and wait for their reply or asking for inputs.
        Use LIST_MESSAGE_WIDGET to present the customer with a list of quick reply options. The customer can select one of the provided options to proceed with the flow.
        Use API_REQUEST_WIDGET to make an API call.
        Use CUSTOM_ACTION_WIDGET to write and run custom JavaScript functions when required. The function result will be stored in variable and used in further flow. Mostly will be used after api requests to extract required data.
        Use LOGIC_WIDGET to evaluate conditions such as if, else if, and else for creating conditional flows.
        Use CONNECT_TO_AGENT_WIDGET to connect the user to a live agent.
        Use END_OF_FLOW_WIDGET to mark the end of the flow.
        """)
    FlowStepType type,
    @JsonProperty(required = true, value = "data") Data data,
    @JsonProperty(required = true, value = "position")
    @JsonPropertyDescription("Position of the node in the flow editor. Define this node position below previous node with enough space.")
    Position position
) {

  @JsonClassDescription("Data represents the data for the node. Format to use variables in the property values {{variable_name}}")
  public record Data(
      @JsonProperty(required = true)
      @JsonPropertyDescription("Text message. If variables needs to used in message use like {{variable_name}}")
      String body,
      @JsonProperty(required = true, value = "transition")
      @JsonPropertyDescription("Represents the edge between two nodes. Each node must have one transition. LIST_MESSAGE_WIDGET and LOGIC_WIDGET can have more than one transitions. END_OF_FLOW_WIDGET should not have any transition. All others should not have more than one transition.")
      List<Transition> transition,
      @JsonPropertyDescription("The variable names that will have the values of the user's response for SEND_WAIT_REPLY_MESSAGE_WIDGET and results of other node types.")
      @JsonProperty(required = true, value = "variables")
      Variables variables,
      @JsonProperty(value = "message_type", required = true)
      @JsonPropertyDescription("Applicable only for node type LIST_MESSAGE_WIDGET, for other types, it should be set to null.")
      MessageTypeEnum message_type,
      @JsonProperty(required = true)
      @JsonPropertyDescription("Applicable only for node type LIST_MESSAGE_WIDGET, for other types, it should be set to null.")
      List<MessageList> messageList,
      @JsonProperty(required = true)
      @JsonPropertyDescription("""
           ES5 JavaScript function code. Applicable only for node type CUSTOM_ACTION_WIDGET, for other types, it should be set to null. below is boilerplate code for the function
               function customFunction(context) {
                 context = JSON.parse(context);
                 // Your code here
                 // context is object which contains all the variable's value in the flow
               }
          """)
      String javaScriptFunction,

      @JsonProperty(required = true)
      @JsonPropertyDescription("API request method, Applicable only for node type API_REQUEST_WIDGET, for other types, it should be set to null.")
      RequestMethod requestMethod,

      @JsonProperty(required = true)
      @JsonPropertyDescription("API url, Applicable only for node type API_REQUEST_WIDGET, for other types, it should be set to null.")
      String url,

      @JsonProperty(required = true)
      @JsonPropertyDescription("API query param json, Applicable only for node type API_REQUEST_WIDGET, for other types, it should be set to null.")
      String query_param_json,

      @JsonProperty(required = true)
      @JsonPropertyDescription("API request headers json, Applicable only for node type API_REQUEST_WIDGET, for other types, it should be set to null.")
      String header_json,

      @JsonProperty(required = true)
      @JsonPropertyDescription("API request body json, Applicable only for node type API_REQUEST_WIDGET, for other types, it should be set to null.")
      String body_json
  ) {

    @JsonClassDescription("Transition represents the link or pathway between two nodes, defining the flow or interaction within the system.")
    public record Transition(
        @JsonProperty(required = true)
        @JsonPropertyDescription("Unique identifier for the transition. Use function GenerateUUID")
        String id,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Label of the transition, this is the text that will be displayed on the transition.")
        String label
    ) {
    }

    @JsonClassDescription("User's response of the step will be stored in reply_message property, and the response of the API_REQUEST_WIDGET or CUSTOM_ACTION_WIDGET will be stored in response property.")
    public record Variables(
        @JsonProperty(required = true, value = "reply_message")
        @JsonPropertyDescription("The variable name that will have the value of the user's response.")
        String reply_message,
        @JsonProperty(required = true, value = "response")
        @JsonPropertyDescription("The variable name that will have the value of the API_REQUEST_WIDGET or CUSTOM_ACTION_WIDGET response. For other node types, it should be set to null.")
        String response
    ) {
    }

    @JsonClassDescription("MessageList represents the list of messages for the LIST_MESSAGE_WIDGET.")
    public record MessageList(
        @JsonProperty(required = true)
        @JsonPropertyDescription("Quick reply option text.")
        String value,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Unique identifier for the transition. Use function GenerateUUID")
        String id,
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the transition that will be triggered when the user selects this option.")
        String transitionId
    ) {
    }
  }

  @JsonClassDescription("The coordinates of the node on the canvas.")
  public record Position(
      @JsonProperty(required = true) int x,
      @JsonProperty(required = true) int y
  ) {
  }
}
