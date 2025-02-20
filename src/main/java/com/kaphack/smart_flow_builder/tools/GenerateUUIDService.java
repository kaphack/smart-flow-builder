package com.kaphack.smart_flow_builder.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class GenerateUUIDService implements Function<GenerateUUIDService.Request, GenerateUUIDService.Response> {

  @Override
  public Response apply(Request request) {
    log.info("Function call: GenerateUUIDService {}", request);
    String uuid = java.util.UUID.randomUUID().toString();
    if (request.type == UUIDType.nodeId) {
      uuid = "dndnode_".concat(uuid);
    }
    return new Response(uuid);
  }

  public record Request(UUIDType type) {
  }

  public record Response(
      @JsonProperty(value = "uuid") String uuid
  ) {
  }

  enum UUIDType {
    nodeId,
    edgeId,
    transitionId,
    somethingElse
  }

}
