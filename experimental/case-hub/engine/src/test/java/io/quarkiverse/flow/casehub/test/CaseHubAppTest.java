package io.quarkiverse.flow.casehub.test;

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
    public void testOne() {
        CaseDefinition theCase = new CaseDefinition();
        theCase.setName("Test Case 1");
        engine.submitCase(theCase);
    }
}
