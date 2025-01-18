package com.kaphack.smart_flow_builder;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FlowStepType {
  @JsonProperty("CHAT_TRIGGER_WIDGET")
  CHAT_TRIGGER_WIDGET {
    @Override
    public String getLabel() {
      return "Fastlane Initiated";
    }
  },
  SEND_MESSAGE_WIDGET {
    @Override
    public String getLabel() {
      return "Message Sent to Customer via Fastlane";
    }
  },
  SEND_WAIT_REPLY_MESSAGE_WIDGET {
    @Override
    public String getLabel() {
      return "Waiting for Customer Reply";
    }
  },
  LIST_MESSAGE_WIDGET,
  API_REQUEST_WIDGET {
    @Override
    public String getLabel() {
      return "API Request";
    }
  },
  CUSTOM_ACTION_WIDGET {
    @Override
    public String getLabel() {
      return "Custom Action";
    }
  },
  LOGIC_WIDGET {
    @Override
    public String getLabel() {
      return "Logic condition evaluation";
    }
  },
  CONNECT_TO_AGENT_WIDGET {
    @Override
    public String getLabel() {
      return "Pushed to Queue: {{TICKET.queueKey}}";
    }
  },
  RATE_TICKET_WIDGET,
  DYNAMIC_OPTIONS_WIDGET,
  UPLOAD_FILE_WIDGET,
  FORM_ASSOCIATE_VALUE_WIDGET,
  DYNAMIC_FORM_WIDGET,
  ORDER_LIST_WIDGET,
  PRODUCT_LIST_WIDGET,
  EXTERNAL_FLOW_WIDGET,
  END_OF_FLOW_WIDGET {
    @Override
    public String getLabel() {
      return "Fastlane Completed";
    }
  },
  REDIRECT_WIDGET,
  LANGUAGE_WIDGET,
  PAUSE_FLOW {
    @Override
    public String getLabel() {
      return "Fastlane Paused";
    }
  },
  RESUME_FLOW {
    @Override
    public String getLabel() {
      return "Fastlane Resumed";
    }
  },
  TERMINATE_FLOW {
    @Override
    public String getLabel() {
      return "Fastlane Terminated";
    }
  },
  UNKNOWN_TYPE;
  // AGENT_ACTION,
  // SYSTEM_ACTION,

  public static FlowStepType optValueOf(String type) {
    try {
      return FlowStepType.valueOf(type);
    } catch (IllegalArgumentException e) {
      return FlowStepType.UNKNOWN_TYPE;
    }
  }

  public String getLabel() {
    return "";
  }
}
