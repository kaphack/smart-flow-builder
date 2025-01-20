package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record ModelOutputFormat(

    @JsonProperty(required = true)
    @JsonPropertyDescription("""
        Status of the assistant chat response,
        NEED_DETAILS: Assistant needs more details from user,
        DONE: Assistant has completed the conversation,
        OKAY: Assistant is okay with the conversation.
        """)
    Status status,

    @JsonProperty(required = true)
    FlowJson flowJson,

    @JsonProperty(required = true)
    @JsonPropertyDescription("If status is NEED_DETAILS, this field contains a question to user asking for additional details.")
    String neededDetails

) {

  enum Status {
    NEED_DETAILS,
    DONE,
    OKAY
  }
}
