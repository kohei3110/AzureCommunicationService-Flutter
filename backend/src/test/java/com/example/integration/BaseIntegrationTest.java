package com.example.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

/**
 * Base class for integration tests.
 * Provides common setup and utilities for integration testing.
 */
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public abstract class BaseIntegrationTest {

    protected static final String TEST_ENDPOINT = System.getenv("ACS_ENDPOINT");
    protected static final String TEST_ACCESS_KEY = System.getenv("ACS_ACCESSKEY");
    protected static final String TEST_NOTIFICATION_HUB_CONNECTION = System.getenv("NOTIFICATION_HUBS_CONNECTION_STRING");
    protected static final String TEST_NOTIFICATION_HUB_PATH = System.getenv("NOTIFICATION_HUBS_PATH");

    @BeforeAll
    void setUp() {
        validateEnvironment();
    }

    private void validateEnvironment() {
        if (TEST_ENDPOINT == null || TEST_ENDPOINT.isEmpty()) {
            throw new IllegalStateException("ACS_ENDPOINT environment variable must be set");
        }
        if (TEST_ACCESS_KEY == null || TEST_ACCESS_KEY.isEmpty()) {
            throw new IllegalStateException("ACS_ACCESSKEY environment variable must be set");
        }
        if (TEST_NOTIFICATION_HUB_CONNECTION == null || TEST_NOTIFICATION_HUB_CONNECTION.isEmpty()) {
            throw new IllegalStateException("NOTIFICATION_HUBS_CONNECTION_STRING environment variable must be set");
        }
        if (TEST_NOTIFICATION_HUB_PATH == null || TEST_NOTIFICATION_HUB_PATH.isEmpty()) {
            throw new IllegalStateException("NOTIFICATION_HUBS_PATH environment variable must be set");
        }
    }
}