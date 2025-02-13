package com.kaphack.smart_flow_builder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Deprecated
public class TestController {

  private final OllamaChatModel chatModel;

  @GetMapping("/chat")
  public String chat(@RequestParam("message") String message) {
    ChatResponse response = chatModel.call(new Prompt(message
//            , OllamaOptions.builder().withFunction("CurrentWeather").build()
        )
    );
    return response.getResult().getOutput().getContent();
  }

//  @GetMapping("/stream")
//  public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//    Prompt prompt = new Prompt(new UserMessage(message));
//    return this.chatModel.stream(prompt);
//  }

}
