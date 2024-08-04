package com.cgtfarmer.demo.factory;

import com.cgtfarmer.demo.config.LiquibaseConfiguration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

public class LiquibaseClientFactory {

  public Liquibase create(LiquibaseConfiguration liquibaseConfiguration)
      throws DatabaseException, SQLException {

    Connection conn = DriverManager.getConnection(
        liquibaseConfiguration.getUrl(),
        liquibaseConfiguration.getUsername(),
        liquibaseConfiguration.getPassword()
    );

    Database database = DatabaseFactory.getInstance()
        .findCorrectDatabaseImplementation(new JdbcConnection(conn));

    Liquibase liquibaseClient = new Liquibase(
        liquibaseConfiguration.getChangelogFilepath(),
        new ClassLoaderResourceAccessor(),
        database
    );

    return liquibaseClient;
  }
}
