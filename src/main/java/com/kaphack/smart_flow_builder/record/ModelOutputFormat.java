package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.kaphack.smart_flow_builder.enums.Status;

public record ModelOutputFormat(

    @JsonProperty(required = true)
    Status status,

    @JsonProperty(required = true)
    FlowJson flowJson,

    @JsonProperty(required = true)
    @JsonPropertyDescription("If status is NEED_DETAILS, this field contains a question to user asking for additional details.")
    String neededDetails

) {
}
