package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FlowEdge(
    @JsonProperty(required = true) String id,
    @JsonProperty(required = true) String label,
    @JsonProperty(required = true) String source,
    @JsonProperty(required = true) String sourceHandle,
    @JsonProperty(required = true) String target
) {
}
