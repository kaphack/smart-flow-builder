package com.kaphack.smart_flow_builder.record;

import com.kaphack.smart_flow_builder.entity.Message;

public record SmartResponse(
    String sessionId,
    Message output
) {
}
