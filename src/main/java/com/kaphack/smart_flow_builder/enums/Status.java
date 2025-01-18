package com.kaphack.smart_flow_builder.enums;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public enum Status {

  @JsonPropertyDescription("If set to NEED_DETAILS, the bot should ask the user additional questions.")
  NEED_DETAILS("Need Details"),

  @JsonPropertyDescription("If set to PROCESSED, the bot should give a proper response without requiring further input.")
  PROCESSED("Processed");

  private final String label;

  Status(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}

