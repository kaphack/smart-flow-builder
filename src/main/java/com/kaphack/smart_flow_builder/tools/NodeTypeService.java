package com.kaphack.smart_flow_builder.tools;

import java.util.List;
import java.util.function.Function;

public class NodeTypeService implements Function<NodeTypeService.Request, NodeTypeService.Response> {

  @Override
  public Response apply(Request request) {
    return new Response(List.of("CHAT_TRIGGER_WIDGET", "SEND_MESSAGE_WIDGET", "SEND_WAIT_REPLY_MESSAGE_WIDGET", "LIST_MESSAGE_WIDGET", "API_REQUEST_WIDGET", "CUSTOM_ACTION_WIDGET", "LOGIC_WIDGET", "CONNECT_TO_AGENT_WIDGET", "RATE_TICKET_WIDGET", "DYNAMIC_OPTIONS_WIDGET", "UPLOAD_FILE_WIDGET", "FORM_ASSOCIATE_VALUE_WIDGET", ""));
  }

  public record Request(
  ) {
  }

  public record Response(List<String> types) {
  }

}
