package io.quarkiverse.flow.casehub.engine;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

import io.quarkiverse.flow.casehub.api.context.StateContext;
import io.quarkiverse.flow.casehub.api.model.CaseDefinition;
import io.smallrye.mutiny.Uni;

public interface CaseHubEngine {

    CompletionStage<UUID> submitCase(CaseDefinition definition);

    CompletionStage<UUID> submitCase(CaseDefinition definition, StateContext stateContext);

    Uni<UUID> startCase(UUID caseId);
}
