package com.kaphack.smart_flow_builder.service.function_callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class GenerateUUIDService implements Function<GenerateUUIDService.Request, GenerateUUIDService.Response> {

  @Override
  public Response apply(Request request) {
    log.info("GenerateUUIDService {}", request);
    return new Response(java.util.UUID.randomUUID().toString());
  }

  public record Request(String type) {
  }

  public record Response(
      @JsonProperty(value = "uuid")
      String uuid) {
  }
}
