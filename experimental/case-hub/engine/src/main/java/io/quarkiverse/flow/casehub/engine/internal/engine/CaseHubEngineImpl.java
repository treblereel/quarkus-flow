package io.quarkiverse.flow.casehub.engine.internal.engine;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkiverse.flow.casehub.api.context.StateContext;
import io.quarkiverse.flow.casehub.api.model.CaseDefinition;
import io.quarkiverse.flow.casehub.engine.CaseHubEngine;
import io.quarkiverse.flow.casehub.engine.internal.context.StateContextImpl;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

@ApplicationScoped
public class CaseHubEngineImpl implements CaseHubEngine {

    @Inject
    EventBus eventBus;

    private final Map<UUID, CaseMetaInfo> definitions = new ConcurrentHashMap<>();

    @Override
    public CompletionStage<UUID> submitCase(CaseDefinition definition) {
        StateContext stateContext = new StateContextImpl();
        return submitCase(definition, stateContext);
    }

    @Override
    public CompletionStage<UUID> submitCase(CaseDefinition definition, StateContext stateContext) {
        CaseMetaInfo metaInfo = new CaseMetaInfo(definition, stateContext);
        definitions.put(definition.getUuid(), metaInfo);
        return eventBus.<UUID> request("casehub.case.created", metaInfo)
                .map(Message::body)
                .toCompletionStage();
    }

    @Override
    public CompletionStage<UUID> startCase(UUID caseId) {
        if (!definitions.containsKey(caseId)) {
            return CompletableFuture.failedFuture(
                    new IllegalStateException("No case definition found for caseId: " + caseId));
        }
        return eventBus.<UUID> request("casehub.case.starting", definitions.get(caseId))
                .map(Message::body)
                .toCompletionStage();
    }
}
