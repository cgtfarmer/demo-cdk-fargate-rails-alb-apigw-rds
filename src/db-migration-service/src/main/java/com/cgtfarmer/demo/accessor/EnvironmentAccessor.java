package com.cgtfarmer.demo.accessor;

public class EnvironmentAccessor {

  public String get(String key) {
    return System.getenv(key);
  }
}
