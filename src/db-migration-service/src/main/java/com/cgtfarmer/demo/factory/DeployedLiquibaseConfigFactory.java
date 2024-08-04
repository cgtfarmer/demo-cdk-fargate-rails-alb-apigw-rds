package com.cgtfarmer.demo.factory;

import com.cgtfarmer.demo.accessor.EnvironmentAccessor;
import com.cgtfarmer.demo.accessor.SecretAccessor;
import com.cgtfarmer.demo.config.LiquibaseConfiguration;
import com.cgtfarmer.demo.dto.DbSecret;
import com.cgtfarmer.demo.exception.LiquibaseConfigCreationException;
import java.io.IOException;

public class DeployedLiquibaseConfigFactory implements LiquibaseConfigFactory {

  private final EnvironmentAccessor environmentAccessor;

  private final SecretAccessor secretAccessor;

  public DeployedLiquibaseConfigFactory(
      EnvironmentAccessor environmentAccessor,
      SecretAccessor secretAccessor
  ) {
    this.environmentAccessor = environmentAccessor;
    this.secretAccessor = secretAccessor;
  }

  public LiquibaseConfiguration create() throws LiquibaseConfigCreationException {
    String awsSessionToken = this.environmentAccessor.get("AWS_SESSION_TOKEN");
    String credsSecretId = this.environmentAccessor.get("DB_CREDS_SECRET_ID");

    DbSecret dbSecret = null;
    try {
      dbSecret = this.secretAccessor.getDbSecret(awsSessionToken, credsSecretId);
    } catch (InterruptedException | IOException e) {
      throw new LiquibaseConfigCreationException(e);
    }

    String jdbcUrl = this.environmentAccessor.get("DB_JDBC_URL");
    String changelogFilepath = this.environmentAccessor.get("DB_CHANGELOG_FILE");

    return LiquibaseConfiguration.builder()
        .url(jdbcUrl)
        .username(dbSecret.getUsername())
        .password(dbSecret.getPassword())
        .changelogFilepath(changelogFilepath)
        .build();
  }
}
