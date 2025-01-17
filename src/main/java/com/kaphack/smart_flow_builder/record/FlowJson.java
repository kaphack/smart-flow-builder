package com.kaphack.smart_flow_builder.record;

import java.util.List;

public record FlowJson(
    List<FlowNode> nodes,
    List<FlowEdge> edges
) {
}
