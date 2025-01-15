package com.kaphack.ai_tech_support_roster.configuration;

import com.kaphack.ai_tech_support_roster.service.MockWeatherService;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class ModelConfiguration {

//  @Bean
//  public OllamaChatModel chatModel() {
//    var ollamaApi = new OllamaApi();
//
//
//    var chatModel = new OllamaChatModel(this.ollamaApi,
//        OllamaOptions.create()
//            .model(OllamaOptions.DEFAULT_MODEL)
//            .temperature(0.9));
//  }

  @Bean
  public FunctionCallback weatherFunctionInfo() {
    return FunctionCallback.builder()
        .description("Get the weather in location") // (2) function description
        .function("CurrentWeather", new MockWeatherService()) // (1) function name
        .inputType(MockWeatherService.Request.class) // (3) function signature
        .build();
  }

}

