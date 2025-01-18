package com.kaphack.smart_flow_builder.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public enum FlowStepType {
//  @JsonProperty("CHAT_TRIGGER_WIDGET")
//  CHAT_TRIGGER_WIDGET {
//    @Override
//    public String getLabel() {
//      return "Fastlane Initiated";
//    }
//  },
  @JsonPropertyDescription("Use this widget to send a plain text message to the customer as part of the flow. The message body should contain simple text (e.g., 'Welcome to our service!'). Ensure no additional JSON structure is included in the body. The description is it Allows you to define/send a text message during the conversation ")
  SEND_MESSAGE_WIDGET {
    @Override
    public String getLabel() {
      return "Message Sent to Customer via Fastlane";
    }
  },

  @JsonPropertyDescription("Use this widget to send a message to the customer and wait for their reply. Once the customer responds, the flow will continue based on the received input.")
  SEND_WAIT_REPLY_MESSAGE_WIDGET {
    @Override
    public String getLabel() {
      return "Waiting for Customer Reply";
    }
  },


  @JsonPropertyDescription("Use this widget to present the customer with a list of quick reply options. The customer can select one of the provided options to proceed with the flow.")
  LIST_MESSAGE_WIDGET,
  @JsonPropertyDescription("Use this widget to make an API call.")
  API_REQUEST_WIDGET {
    @Override
    public String getLabel() {
      return "API Request";
    }
  },

  @JsonPropertyDescription("Use this widget to execute custom JavaScript actions after api requests to extract required data.")
  CUSTOM_ACTION_WIDGET {
    @Override
    public String getLabel() {
      return "Custom Action";
    }
  },

  @JsonPropertyDescription("Use this widget to evaluate conditions such as if, else if, and else for creating complex conditional flows.")
  LOGIC_WIDGET {
    @Override
    public String getLabel() {
      return "Logic condition evaluation";
    }
  },

  @JsonPropertyDescription("Use this widget to connect the user to a live agent when the bot cannot handle the flow or specific scenarios require human intervention.")
  CONNECT_TO_AGENT_WIDGET {
    @Override
    public String getLabel() {
      return "Pushed to Queue: {{TICKET.queueKey}}";
    }
  },

//  RATE_TICKET_WIDGET,
//  DYNAMIC_OPTIONS_WIDGET,
//  UPLOAD_FILE_WIDGET,
//  FORM_ASSOCIATE_VALUE_WIDGET,
//  DYNAMIC_FORM_WIDGET,
//  ORDER_LIST_WIDGET,
//  PRODUCT_LIST_WIDGET,
//  EXTERNAL_FLOW_WIDGET,


  @JsonPropertyDescription("Use this widget to mark the completion of the flow. This signifies the end of the bot's interaction with the user for the current session.")
  END_OF_FLOW_WIDGET {
    @Override
    public String getLabel() {
      return "Fastlane Completed";
    }
  },
//  REDIRECT_WIDGET,
//  LANGUAGE_WIDGET,
//  PAUSE_FLOW {
//    @Override
//    public String getLabel() {
//      return "Fastlane Paused";
//    }
//  },
//  RESUME_FLOW {
//    @Override
//    public String getLabel() {
//      return "Fastlane Resumed";
//    }
//  },
//  TERMINATE_FLOW {
//    @Override
//    public String getLabel() {
//      return "Fastlane Terminated";
//    }
//  },
//  UNKNOWN_TYPE
  ;
  // AGENT_ACTION,
  // SYSTEM_ACTION,

//  public static FlowStepType optValueOf(String type) {
//    try {
//      return FlowStepType.valueOf(type);
//    } catch (IllegalArgumentException e) {
////      return FlowStepType.UNKNOWN_TYPE;
//
//    }
//  }

  public String getLabel() {
    return "";
  }
}
