package com.kaphack.smart_flow_builder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaphack.smart_flow_builder.dto.OllamaChatRequestDto;
import com.kaphack.smart_flow_builder.dto.OllamaChatResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@Slf4j
@Deprecated
public class ModelService {


  @Autowired
  private ObjectMapper objectMapper;

  @Value("${spring.ai.ollama.base-url}")
  private String ollamaBaseUrl;

  public OllamaChatResponseDto ollamaChat(OllamaChatRequestDto ollamaRequestDto) {
    try {
      String url = ollamaBaseUrl + "/api/chat";
      HttpEntity<?> httpEntity = new HttpEntity<>(objectMapper.convertValue(ollamaRequestDto, HashMap.class));
      return new RestTemplate().exchange(url, HttpMethod.POST, httpEntity, OllamaChatResponseDto.class)
          .getBody();
    } catch (Exception e) {
      log.error("Error in ollamaChat()", e);
      return null;
    }
  }
}
