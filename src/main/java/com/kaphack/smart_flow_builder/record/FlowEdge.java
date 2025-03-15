package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record FlowEdge(
    @JsonProperty(required = true)
    @JsonPropertyDescription("UUID of the edge.")
    String id,
    @JsonProperty(required = true)
    @JsonPropertyDescription("The label of the sourceHandle node's transition.")
    String label,
    @JsonProperty(required = true)
    @JsonPropertyDescription("The ID of the source node.")
    String source,
    @JsonProperty(required = true)
    @JsonPropertyDescription("The transition id of the source node. It will be available in source node's data -> transition item -> id")
    String sourceHandle,
    @JsonProperty(required = true)
    @JsonPropertyDescription("The ID of the target node.")
    String target//,
//    @JsonProperty(defaultValue = "CUSTOM_EDGE", required = true)
//    @JsonPropertyDescription("value will be CUSTOM_EDGE always")
//    String type,
//    @JsonProperty(defaultValue = "true", required = true)
//    @JsonPropertyDescription("value will be true always")
//    Boolean animated
) {
}
