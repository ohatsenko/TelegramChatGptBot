package com.smarttek.chatgptbot.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {
    private static final String DB_IMAGE = "postgres";
    private static final String TEST_DB_URL = "CONTAINER.URL=";
    private static final String TEST_DB_USERNAME = "CONTAINER.USERNAME=";
    private static final String TEST_DB_PASSWORD = "CONTAINER.PASSWORD=";
    private static PostgresTestContainer postgresTestContainer;

    private PostgresTestContainer() {
        super(DB_IMAGE);
    }

    public static synchronized PostgresTestContainer getInstance() {
        if (postgresTestContainer == null) {
            postgresTestContainer = new PostgresTestContainer();
        }
        return postgresTestContainer;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty(TEST_DB_URL, postgresTestContainer.getJdbcUrl());
        System.setProperty(TEST_DB_USERNAME, postgresTestContainer.getUsername());
        System.setProperty(TEST_DB_PASSWORD, postgresTestContainer.getPassword());
    }

    @Override
    public void stop() {
    }
}
