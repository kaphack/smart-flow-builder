package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record FlowEdge(
    @JsonProperty(required = true) String id,
    @JsonProperty(required = true) String label,
    @JsonProperty(required = true) String source,
    @JsonProperty(required = true) String sourceHandle,
    @JsonProperty(required = true) String target,
    @JsonProperty(defaultValue = "CUSTOM_EDGE", required = true) @JsonPropertyDescription("CUSTOM_EDGE") String type,
    @JsonProperty(defaultValue = "true", required = true) Boolean animated
) {
}
