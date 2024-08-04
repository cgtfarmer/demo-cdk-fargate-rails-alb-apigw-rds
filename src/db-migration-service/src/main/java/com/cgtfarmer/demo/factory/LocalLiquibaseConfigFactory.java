package com.cgtfarmer.demo.factory;

import com.cgtfarmer.demo.accessor.EnvironmentAccessor;
import com.cgtfarmer.demo.config.LiquibaseConfiguration;
import com.cgtfarmer.demo.exception.LiquibaseConfigCreationException;

public class LocalLiquibaseConfigFactory implements LiquibaseConfigFactory {

  private final EnvironmentAccessor environmentAccessor;

  public LocalLiquibaseConfigFactory(EnvironmentAccessor environmentAccessor) {
    this.environmentAccessor = environmentAccessor;
  }

  public LiquibaseConfiguration create() throws LiquibaseConfigCreationException {
    String dbUsername = this.environmentAccessor.get("DB_USERNAME");
    String dbPassword = this.environmentAccessor.get("DB_PASSWORD");
    String jdbcUrl = this.environmentAccessor.get("DB_JDBC_URL");
    String changelogFilepath = this.environmentAccessor.get("DB_CHANGELOG_FILE");

    return LiquibaseConfiguration.builder()
        .url(jdbcUrl)
        .username(dbUsername)
        .password(dbPassword)
        .changelogFilepath(changelogFilepath)
        .build();
  }
}
