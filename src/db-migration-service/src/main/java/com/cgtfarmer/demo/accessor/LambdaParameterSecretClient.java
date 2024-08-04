package com.cgtfarmer.demo.accessor;

import com.cgtfarmer.demo.dto.SecretsManagerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class LambdaParameterSecretClient {

  private static String tokenHeaderKey = "X-Aws-Parameters-Secrets-Token";

  private static String secretAccessUrl = "http://localhost:2773/secretsmanager/get";

  private final HttpClient httpClient;

  private final ObjectMapper mapper;

  public LambdaParameterSecretClient(HttpClient httpClient, ObjectMapper mapper) {
    this.httpClient = httpClient;
    this.mapper = mapper;
  }

  public SecretsManagerResponse getSecret(String awsSessionToken, String secretId)
      throws InterruptedException, IOException {

    String url = String.format("%s?secretId=%s", secretAccessUrl, secretId);

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .setHeader(tokenHeaderKey, awsSessionToken)
        .build();

    HttpResponse<String> response = this.httpClient.send(
      request,
      BodyHandlers.ofString()
    );

    String body = response.body();

    SecretsManagerResponse mappedResponse = mapper.readValue(body, SecretsManagerResponse.class);

    return mappedResponse;
  }
}
