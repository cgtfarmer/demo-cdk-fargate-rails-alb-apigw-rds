package com.cgtfarmer.demo;

import com.cgtfarmer.demo.config.DependencyGraph;
import com.cgtfarmer.demo.exception.ExceptionUtils;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;

public class Main {
  public static void main(String[] args) {

    try {
      DependencyGraph graph = DependencyGraph.getInstance();

      Liquibase liquibaseClient = graph.getLiquibaseClient();

      liquibaseClient.update(new Contexts(), new LabelExpression());

      liquibaseClient.close();
    } catch (Exception e) {
      System.out.println("DB migration failed: " + ExceptionUtils.mapStackTraceToString(e));
    }
  }
}
