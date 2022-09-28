package testUtils;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.atomic.AtomicBoolean;

public class PostgresTestContainer implements BeforeAllCallback {

    private static final AtomicBoolean containerStarted = new AtomicBoolean(false);

    @Container
    private static final GenericContainer postgres = new GenericContainer(DockerImageName.parse("postgres:13.2"))
        .withExposedPorts(5432)
        .withEnv("POSTGRES_USER", "postgres")
        .withEnv("POSTGRES_PASSWORD", "password")
        .withEnv("POSTGRES_DB", "postgres");

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (!containerStarted.get()) {
            postgres.start();
            containerStarted.set(true);
        }
    }
}
