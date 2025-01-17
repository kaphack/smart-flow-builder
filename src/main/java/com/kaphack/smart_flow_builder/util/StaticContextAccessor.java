package com.kaphack.smart_flow_builder.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class StaticContextAccessor {
  private static ApplicationContext context;

  @Autowired
  public StaticContextAccessor(ApplicationContext applicationContext) {
    context = applicationContext;
  }

  public static <T> T getBean(Class<T> tClass) {
    return context.getBean(tClass);
  }

  public static <T> T getBean(String name, Class<T> tClass) {
    return context.getBean(name, tClass);
  }

}
