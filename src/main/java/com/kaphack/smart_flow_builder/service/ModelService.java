package com.kaphack.smart_flow_builder.service;

import com.kaphack.smart_flow_builder.dto.OllamaChatRequestDto;
import com.kaphack.smart_flow_builder.dto.OllamaChatResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
public class ModelService {

  private final RestTemplate restTemplate;

  @Value("${spring.ai.ollama.base-url}")
  private String ollamaBaseUrl;

  public OllamaChatResponseDto ollamaChat(OllamaChatRequestDto ollamaRequestDto) {
    String url = ollamaBaseUrl + "/your-endpoint"; // Update with your specific endpoint
    HttpEntity<?> httpEntity = new HttpEntity<>(ollamaRequestDto);
    return restTemplate.exchange(url, HttpMethod.POST, httpEntity, OllamaChatResponseDto.class)
        .getBody();
  }
}
