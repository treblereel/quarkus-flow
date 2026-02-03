package io.quarkiverse.flow.casehub.test;

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
        CompletionStage<UUID> futureSubmitCase = engine.submitCase(caseDefinition);
        UUID caseId = futureSubmitCase.toCompletableFuture().get();

        CompletionStage<UUID> futureStartCase = engine.startCase(caseId);
        futureStartCase.toCompletableFuture().get();

        System.out.println("Test received case ID: " + caseId);
    }
}
