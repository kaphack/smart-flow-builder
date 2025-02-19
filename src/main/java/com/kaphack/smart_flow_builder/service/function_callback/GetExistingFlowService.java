package com.kaphack.smart_flow_builder.service.function_callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class GetExistingFlowService implements Function<GetExistingFlowService.Request, GetExistingFlowService.Response> {

    @SneakyThrows
    @Override
    public Response apply(Request request) {
        log.info("Function call: GetExistingFlowService {}", request);
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString("https://democrm.kapturecrm.com/ms/cbfs/chat-bot-configuration/get-chat-bot-configuration-data")
                .queryParam("flowId", request.flowId)
                .build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic ZGVtb2NybTpEZW1vY3JtQDEyMw==");
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        Map<String, Object> resp = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Map.class).getBody();
        Map<String, Object> data = (Map<String, Object>) resp.get("data");
        data.remove("viewport");
        data.remove("flowBuilderPreference");
        ObjectMapper objectMapper = new ObjectMapper();
        return new Response(objectMapper.writeValueAsString(data));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Request(String flowId) {
    }

    public record Response(Object flowJson) {
    }

}
