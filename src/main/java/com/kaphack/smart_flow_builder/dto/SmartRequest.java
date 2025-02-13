package com.kaphack.smart_flow_builder.dto;

public record SmartRequest(String model, String sessionId, String promptText, String promptImage,
                           String inputFlowJson) {
}
