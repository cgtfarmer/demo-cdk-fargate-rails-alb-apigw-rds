package com.cgtfarmer.demo.exception;

public class LiquibaseConfigCreationException extends Exception {

  public LiquibaseConfigCreationException(Throwable cause) {
    super("Failed to create Liquibase configuration", cause);
  }
}
