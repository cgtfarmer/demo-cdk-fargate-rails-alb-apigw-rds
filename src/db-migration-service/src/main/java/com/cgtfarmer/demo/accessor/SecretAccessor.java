package com.cgtfarmer.demo.accessor;

import com.cgtfarmer.demo.dto.DbSecret;
import com.cgtfarmer.demo.dto.SecretsManagerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class SecretAccessor {

  private final ObjectMapper mapper;

  private final LambdaParameterSecretClient lambdaParameterSecretClient;

  public SecretAccessor(
      ObjectMapper mapper,
      LambdaParameterSecretClient lambdaParameterSecretClient
  ) {
    this.mapper = mapper;
    this.lambdaParameterSecretClient = lambdaParameterSecretClient;
  }

  public DbSecret getDbSecret(String awsSessionToken, String secretId)
      throws InterruptedException, IOException {

    SecretsManagerResponse secretResponse = this.lambdaParameterSecretClient.getSecret(
        awsSessionToken,
        secretId
    );

    DbSecret secret = this.mapper.readValue(
        secretResponse.getSecretString(),
        DbSecret.class
    );

    return secret;
  }
}
