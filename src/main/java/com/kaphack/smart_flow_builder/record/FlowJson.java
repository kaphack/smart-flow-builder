package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FlowJson(
    @JsonProperty(required = true) List<FlowNode> nodes,
    @JsonProperty(required = true) List<FlowEdge> edges
) {
}
