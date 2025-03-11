package com.kaphack.smart_flow_builder.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record JavascriptOutputFormat(

    @JsonProperty(required = true)
    @JsonPropertyDescription("""
            Boilerplate:

            function customFunction(variables) {
                variables = JSON.parse(variables);
                // Your code here
                // 'variables' contains all flow variable values
            }

            Notes:
            - Use ES5 syntax only.
            - 'variables' is a JSON string; parse before use.
        """)
    String javascriptFunction

) {
}
