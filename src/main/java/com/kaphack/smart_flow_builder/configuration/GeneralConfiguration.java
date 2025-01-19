package com.kaphack.smart_flow_builder.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeneralConfiguration {

//  @Bean
//  public ObjectMapper objectMapper() {
//    return new ObjectMapper()
//        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//        .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
//        .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
//        ;
//  }


  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}

