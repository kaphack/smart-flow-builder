package com.kaphack.smart_flow_builder.service;

import com.kaphack.smart_flow_builder.service.function_callback.GenerateUUIDService;
import com.kaphack.smart_flow_builder.service.function_callback.GetExistingFlowService;
import org.springframework.ai.model.function.FunctionCallback;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallbackService {

  public static final List<FunctionCallback> functionCallbackList = new ArrayList<>();

  static {
    functionCallbackList.add(
        FunctionCallback.builder()
            .function("GenerateUUID", new GenerateUUIDService())
            .description("Use this in creating new flow to generate id, transition id for node and edge in flowJson")
            .inputType(GenerateUUIDService.Request.class)
            .build()
    );
    functionCallbackList.add(
        FunctionCallback.builder()
            .function("GetExistingFlow", new GetExistingFlowService())
            .description("Use this to get existing flow")
            .inputType(GetExistingFlowService.Request.class)
            .build()
    );
  }

}
