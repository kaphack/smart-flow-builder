package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ModelOutputFormat(
    @JsonProperty(required = true) String status,
    @JsonProperty(required = true) FlowJson flowJson,
    @JsonProperty(required = true) String message,
    String sessionId
) {
}
