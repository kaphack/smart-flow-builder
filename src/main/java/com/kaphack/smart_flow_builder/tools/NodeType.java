package com.kaphack.smart_flow_builder.tools;


import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Deprecated
public class NodeType implements Function<NodeType.Request, NodeType.Response> {

  @Override
  public Response apply(Request request) {
    try {
      return new Response(FlowStepType.valueOf(request.node_type).getLabel());
    } catch (IllegalArgumentException e) {
      return new Response(FlowStepType.UNKNOWN_TYPE.getLabel());
    }
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonClassDescription("Weather API request")
  public record Request(@JsonProperty(required = true,
      value = "node_type") @JsonPropertyDescription("The type of node, which will decide it's functionality in the react chatbot flow builder.") String node_type) {

  }

  public record Response(String type) {
  }

  @Slf4j
  public enum FlowStepType {
    CHAT_TRIGGER_WIDGET {
      public String getLabel() {
        return "Fastlane Initiated";
      }
    },
    SEND_MESSAGE_WIDGET {
      public String getLabel() {
        return "Message Sent to Customer via Fastlane";
      }
    },
    SEND_WAIT_REPLY_MESSAGE_WIDGET {
      public String getLabel() {
        return "Waiting for Customer Reply";
      }
    },
    LIST_MESSAGE_WIDGET,
    API_REQUEST_WIDGET {
      public String getLabel() {
        return "API Request";
      }
    },
    CUSTOM_ACTION_WIDGET {
      public String getLabel() {
        return "Custom Action";
      }
    },
    LOGIC_WIDGET {
      public String getLabel() {
        return "Logic condition evaluation";
      }
    },
    CONNECT_TO_AGENT_WIDGET {
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
      public String getLabel() {
        return "Fastlane Completed";
      }
    },
    REDIRECT_WIDGET,
    LANGUAGE_WIDGET,
    PAUSE_FLOW {
      public String getLabel() {
        return "Fastlane Paused";
      }
    },
    RESUME_FLOW {
      public String getLabel() {
        return "Fastlane Resumed";
      }
    },
    TERMINATE_FLOW {
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
}
