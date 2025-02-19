package com.kaphack.smart_flow_builder.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaphack.smart_flow_builder.util.StringUtils;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class SmartFlowRequestDto {
    private String model;
    private String sessionId;
    @NotNull
    private String promptText;
    private String promptImage;
    private String inputFlowJson; // JSONObject
    private String flowId;

    @JsonIgnore
    private boolean isNewSession;

    public boolean isNewSession() {
        return isNewSession || StringUtils.isNullOrEmpty(sessionId);
    }

    public String getSessionId() {
        if (StringUtils.isNullOrEmpty(sessionId)) {
            sessionId = UUID.randomUUID().toString();
            isNewSession = true;
        }
        return sessionId;
    }
}
