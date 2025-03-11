package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record JavascriptOutputFormat(

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
}
