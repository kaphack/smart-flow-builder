package com.kaphack.smart_flow_builder.service.function_callback;

import java.util.function.Function;

public class GenerateUUIDService implements Function<GenerateUUIDService.Request, GenerateUUIDService.Response> {

    @Override
    public Response apply(Request request) {
        return new Response(java.util.UUID.randomUUID().toString());
    }

    public record Request() {
    }

    public record Response(String uuid) {
    }
}
