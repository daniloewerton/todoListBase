package com.daniloewerton.todolist;

import org.junit.ClassRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class TestContainersConfiguration {

    @ClassRule
    public static final GenericContainer<?> REDIS_CONTAINER = createRedisContainer();

    @ClassRule
    public static final PostgreSQLContainer<?> DATABASE_CONTAINER = createDatabaseContainer();

    static class TestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(final ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.redis.host=", REDIS_CONTAINER.getHost(),
                    "spring.datasource.redis.port=", String.valueOf(REDIS_CONTAINER.getMappedPort(6380))
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        DATABASE_CONTAINER.withInitScript("init_script.sql");
        DATABASE_CONTAINER.start();

        var properties = TestPropertyValues.of(
          "spring.datasource.url=", DATABASE_CONTAINER.getJdbcUrl(),
                "spring.datasource.username=", DATABASE_CONTAINER.getUsername(),
                "spring.datasource.password=", DATABASE_CONTAINER.getPassword()
        );

        properties.applyTo(configurableApplicationContext);
    }

    private static GenericContainer<?> createRedisContainer() {
        final GenericContainer<?> container = new GenericContainer<>("redis:6.2.6").withExposedPorts(6380);
        container.start();
        container.waitingFor(Wait.forHealthcheck());
        return container;
    }

    private static PostgreSQLContainer<?> createDatabaseContainer() {
        final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15-alpine").withExposedPorts(5433);
        container.start();
        container.waitingFor(Wait.forHealthcheck());
        return container;
    }
}
