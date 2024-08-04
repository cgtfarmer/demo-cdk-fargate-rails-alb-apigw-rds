package com.cgtfarmer.demo.exception;

public class DependencyGraphException extends Exception {

  public DependencyGraphException(Throwable cause) {
    super("Failed to generate dependency graph", cause);
  }
}
