package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.kaphack.smart_flow_builder.enums.Status;

public record ModelOutputFormat(
    // give enum for st
    @JsonProperty(required = true) Status status,
    @JsonProperty(required = true) FlowJson flowJson,
    // Add a description that if status is need details then it should aks for details else give proper json
    @JsonProperty(required = true)
    @JsonPropertyDescription("If status is NEED_DETAILS, this field contains a prompt asking for additional details. If status is PROCESSED, this field contains a proper JSON response.")
    String message,
    String sessionId
) {
}
