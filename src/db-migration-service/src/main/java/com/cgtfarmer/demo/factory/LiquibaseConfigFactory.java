package com.cgtfarmer.demo.factory;

import com.cgtfarmer.demo.config.LiquibaseConfiguration;
import com.cgtfarmer.demo.exception.LiquibaseConfigCreationException;

public interface LiquibaseConfigFactory {

  LiquibaseConfiguration create() throws LiquibaseConfigCreationException;
}
