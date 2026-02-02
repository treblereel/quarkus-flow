package io.quarkiverse.flow.casehub.engine.internal.engine;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkiverse.flow.casehub.api.model.CaseDefinition;
import io.quarkus.vertx.ConsumeEvent;

@ApplicationScoped
public class OnCaseStartingProcessor {

    @ConsumeEvent("casehub.case.submitted")
    public void onEvent(CaseDefinition theCase) {
        System.out.println("Processing case starting for case ID: " + theCase.getUuid());
    }
}
