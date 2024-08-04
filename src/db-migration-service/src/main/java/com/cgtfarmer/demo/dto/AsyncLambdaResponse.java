package com.cgtfarmer.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AsyncLambdaResponse {

  private String Status;

  private String Reason;
}
