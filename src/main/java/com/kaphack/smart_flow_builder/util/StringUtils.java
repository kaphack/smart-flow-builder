package com.kaphack.smart_flow_builder.util;

import org.springframework.stereotype.Service;

@Service
public class StringUtils {

  public static boolean isNullOrEmpty(String input) {
    return input == null || input.isEmpty();
  }

}
