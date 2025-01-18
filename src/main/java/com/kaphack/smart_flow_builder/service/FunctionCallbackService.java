package com.kaphack.smart_flow_builder.service;

import com.kaphack.smart_flow_builder.service.function_callback.GenerateUUIDService;
import com.kaphack.smart_flow_builder.service.function_callback.MockWeatherService;
import com.kaphack.smart_flow_builder.service.function_callback.NodeTypeService;
import org.springframework.ai.model.function.FunctionCallback;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallbackService {

  public static final List<FunctionCallback> functionCallbackList = new ArrayList<>();

  static {
    functionCallbackList.add(
        FunctionCallback.builder()
            .description("Get the weather in location") // (2) function description
            .function("CurrentWeather", new MockWeatherService()) // (1) function name
            .inputType(MockWeatherService.Request.class) // (3) function signature
            .build()
    );
    functionCallbackList.add(
        FunctionCallback.builder()
            .description("Get available node types to use for type in node object in flowJson")
            .function("GetNodeTypes", new NodeTypeService())
            .inputType(NodeTypeService.Request.class)
            .build()
    );
    functionCallbackList.add(
        FunctionCallback.builder()
            .description("Generate id for node object and edge object in flowJson")
            .function("GenerateUUID", new GenerateUUIDService())
            .inputType(GenerateUUIDService.Request.class)
            .build()
    );
  }

}
