package com.kaphack.smart_flow_builder.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ModelConfiguration {

//  private final MessageRepository messageRepository;

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

//  @Bean
//  public FunctionCallback weatherFunctionInfo() {
//    return FunctionCallback.builder()
//        .description("Get the weather in location") // (2) function description
//        .function("CurrentWeather", new MockWeatherService()) // (1) function name
//        .inputType(MockWeatherService.Request.class) // (3) function signature
//        .build();
//  }

  @Bean
  public OpenAiChatModel openAiChatModel() {
//    Message message = messageRepository.findById(1L).orElseThrow();
    String string = System.getenv("OPENAI_API_KEY");
    var openAiApi = new OpenAiApi(string);
    var openAiChatOptions = OpenAiChatOptions.builder()
        .model(OpenAiApi.ChatModel.GPT_4_TURBO)
        .temperature(1.0)
        .build();
    return new OpenAiChatModel(openAiApi, openAiChatOptions);
  }

}

