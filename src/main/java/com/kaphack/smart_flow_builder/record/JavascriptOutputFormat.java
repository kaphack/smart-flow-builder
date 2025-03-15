package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record JavascriptOutputFormat(

    @JsonProperty(required = true)
    @JsonPropertyDescription("""
        Status of the response. If the user's prompt is not clear, status should be error and dont generate javascriptFunction.
        """)
    Status status,

    @JsonProperty(required = true)
    @JsonPropertyDescription(""" 
        Expected JavaScript function format:

        function customFunction(variables) {
            variables = JSON.parse(variables);
            // Your logic here
        }
        """)
    String javascriptFunction

) {

  enum Status {
    success,
    error
  }

}
