package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record ModelOutputFormat(

    @JsonProperty(required = true)
    @JsonPropertyDescription("""
        Status of the assistant chat response,
        NeedDetails: Assistant needs more details from user,
        Done: Assistant has generated a flow json,
        Okay: Assistant is okay with the conversation.
        """)
    Status status,

    @JsonProperty(required = true)
    FlowJson flowJson,

    @JsonProperty(required = true)
    @JsonPropertyDescription("If status is NeedDetails, this field contains a question to user asking for additional details.")
    String questionToUser

) {

  enum Status {
    NeedDetails,
    Done,
    Okay,
  }

//  enum StatusDescription {
//    RequestedFlowIsGenerated,
//    RequestedEditFlowIsDone,
//    RequestedJavascriptIsGenerated,
//    QueryIsNotRelatedToBuildingFlow,
//  }
}
