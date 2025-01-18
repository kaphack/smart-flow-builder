package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;

public record FlowJson(
    @JsonProperty(required = true)
    @JsonPropertyDescription("List of nodes in the flow, each node represents a step in the flow.")
    List<FlowNode> nodes,
    @JsonProperty(required = true)
    @JsonPropertyDescription("List of edges in the flow, each edge represents a connection between two nodes.")
    List<FlowEdge> edges
) {
}
