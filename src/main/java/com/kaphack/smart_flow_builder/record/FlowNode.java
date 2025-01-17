package com.kaphack.smart_flow_builder.record;

public record FlowNode(
    String id,
    String type,
    Data data
) {

  record Data(
      String body,
      Transition transition,
      Variables variables,
      String message_type,
      MessageList messageList
  ) {

    record Transition(
        String id,
        String label
    ) {
    }

    record Variables(
        String reply_message
    ) {
    }

    record MessageList(
        String value,
        String id,
        String transitionId
    ) {
    }

  }
}
