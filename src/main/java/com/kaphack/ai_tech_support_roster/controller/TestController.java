package com.kaphack.ai_tech_support_roster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class TestController {

  private final OllamaChatModel chatModel;

  @GetMapping("/chat")
  public String chat(@RequestParam("message") String message) {
    ChatResponse response = chatModel.call(
        new Prompt(
            message,
            OllamaOptions.builder()
                .withModel(OllamaModel.LLAMA2)
                .withTemperature(0.4)
                .build()
        ));
    return response.getResult().getOutput().getContent();
  }

//  @GetMapping("/stream")
//  public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//    Prompt prompt = new Prompt(new UserMessage(message));
//    return this.chatModel.stream(prompt);
//  }

}
