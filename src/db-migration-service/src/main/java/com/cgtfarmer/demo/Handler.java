// Runtime: Java 17
package com.cgtfarmer.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.CloudFormationCustomResourceEvent;
import com.cgtfarmer.demo.config.DependencyGraph;
import com.cgtfarmer.demo.dto.AsyncLambdaResponse;
import com.cgtfarmer.demo.exception.ExceptionUtils;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;

/**
 * Lambda Handler.
 *
 * Note: RequestHandler<X, Y>
 *   X = event type
 *   Y = return type
 */
public class Handler implements RequestHandler<CloudFormationCustomResourceEvent, AsyncLambdaResponse> {

  @Override
  public AsyncLambdaResponse handleRequest(
      CloudFormationCustomResourceEvent event,
      Context context
  ) {
    LambdaLogger logger = context.getLogger();

    try {
      DependencyGraph graph = DependencyGraph.getInstance();

      Liquibase liquibaseClient = graph.getLiquibaseClient();

      liquibaseClient.update(new Contexts(), new LabelExpression());

      liquibaseClient.close();
    } catch (Exception e) {
      logger.log("DB migration failed: " + ExceptionUtils.mapStackTraceToString(e));

      AsyncLambdaResponse response = AsyncLambdaResponse.builder()
          .Status("FAILED")
          .Reason(e.getMessage())
          .build();

      logger.log(response.toString());

      return response;
    }

    AsyncLambdaResponse response = AsyncLambdaResponse.builder()
        .Status("SUCCESS")
        .build();

    logger.log(response.toString());

    return response;
  }
}
