package io.quarkiverse.flow.casehub.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkiverse.flow.casehub.api.model.CaseDefinition;
import io.quarkiverse.flow.casehub.engine.CaseHubEngine;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CaseHubAppTest {

    @Inject
    CaseHubEngine engine;

    @Test
    public void testOne() throws Exception {
        CaseDefinition caseDefinition = new CaseDefinition();
        caseDefinition.setName("Test Case 1");
        caseDefinition.setVersion("1.0");

        CompletionStage<UUID> stage = engine.submitCase(caseDefinition)
                .thenCompose(engine::startCase);

        UUID caseId = assertTimeoutPreemptively(
                Duration.ofSeconds(5),
                () -> stage.toCompletableFuture().join());

        assertNotNull(caseId, "caseId must not be null");
    }
}
