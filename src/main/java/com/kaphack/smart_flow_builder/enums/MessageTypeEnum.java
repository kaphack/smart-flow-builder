package com.kaphack.smart_flow_builder.enums;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public enum MessageTypeEnum {
  @JsonPropertyDescription("A Quick Reply Dropdown Options")
  menu,
  @JsonPropertyDescription("A Quick Reply Button Options")
  buttons,
  @JsonPropertyDescription("A normal text Message")
  text,
}
