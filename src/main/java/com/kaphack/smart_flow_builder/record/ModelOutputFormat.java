package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record ModelOutputFormat(

    @JsonProperty(required = true)
    @JsonPropertyDescription("status of the output, if user prompt is not clear or any additional information required status will be NEED_DETAILS else DONE if user request is process successfully or OKAY if everything is fine.")
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
