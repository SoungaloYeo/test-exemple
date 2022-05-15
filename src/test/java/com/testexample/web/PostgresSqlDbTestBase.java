package com.testexample.web;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresSqlDbTestBase {

    private static PostgreSQLContainer database = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("integration-tests-db")
            .withUsername("yeo")
            .withPassword("yeo");


    static {
        database.start();
    }
}
