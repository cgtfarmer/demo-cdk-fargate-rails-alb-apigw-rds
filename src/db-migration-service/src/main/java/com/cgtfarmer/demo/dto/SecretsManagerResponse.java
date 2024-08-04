package com.cgtfarmer.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SecretsManagerResponse {

  @JsonProperty("ARN")
  private String arn;

  @JsonProperty("CreatedDate")
  private LocalDateTime createdDate;

  @JsonProperty("Name")
  private String name;

  @JsonProperty("SecretString")
  private String secretString;

  @JsonProperty("VersionId")
  private String versionId;
}
