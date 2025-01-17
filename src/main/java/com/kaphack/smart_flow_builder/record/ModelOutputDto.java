package com.kaphack.smart_flow_builder.record;

public record ModelOutputDto(
    String status,
    FlowJson flowJson,
    String message
) {
}
