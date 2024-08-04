package com.cgtfarmer.demo.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DbSecret {

  private String password;

  private String dbname;

  private String engine;

  private String port;

  private String dbInstanceIdentifier;

  private String host;

  private String username;
}
