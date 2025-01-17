package com.kaphack.smart_flow_builder.record;

public record ModelOutputFormat(
    String status,
    FlowJson flowJson,
    String message
) {
}
