package com.kaphack.smart_flow_builder.service;

import com.kaphack.smart_flow_builder.service.function_callback.GenerateUUIDService;
import org.springframework.ai.model.function.FunctionCallback;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallbackService {

  public static final List<FunctionCallback> functionCallbackList = new ArrayList<>();

  static {
    functionCallbackList.add(
        FunctionCallback.builder()
            .function("GenerateUUID", new GenerateUUIDService())
            .description("Use this to generate id, transition id for node and edge in flowJson")
            .inputType(GenerateUUIDService.Request.class)
            .build()
    );
  }

}
