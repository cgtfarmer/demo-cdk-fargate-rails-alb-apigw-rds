package com.cgtfarmer.demo.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LiquibaseConfiguration {

  private String url;

  private String username;

  private String password;

  private String changelogFilepath;
}
