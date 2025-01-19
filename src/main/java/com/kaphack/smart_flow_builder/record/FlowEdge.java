package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record FlowEdge(
    @JsonProperty(required = true)
    @JsonPropertyDescription("UUID of the edge.")
    String id,
    @JsonProperty(required = true)
    @JsonPropertyDescription("Label of the edge, this is the text that will be displayed on the edge.")
    String label,
    @JsonProperty(required = true)
    @JsonPropertyDescription("ID of the source node of the edge.")
    String source,
    @JsonProperty(required = true)
    @JsonPropertyDescription("Handle of the source node of the edge.")
    String sourceHandle,
    @JsonProperty(required = true)
    @JsonPropertyDescription("ID of the target node of the edge.")
    String target,
    @JsonProperty(defaultValue = "CUSTOM_EDGE", required = true) @JsonPropertyDescription("CUSTOM_EDGE") String type,
    @JsonProperty(defaultValue = "true", required = true) Boolean animated
) {
}
