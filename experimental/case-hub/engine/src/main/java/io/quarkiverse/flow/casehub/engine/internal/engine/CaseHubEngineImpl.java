package io.quarkiverse.flow.casehub.engine.internal.engine;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import io.quarkiverse.flow.casehub.api.context.StateContext;
import io.quarkiverse.flow.casehub.engine.internal.context.StateContextImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkiverse.flow.casehub.api.model.CaseDefinition;
import io.quarkiverse.flow.casehub.engine.CaseHubEngine;
import io.vertx.core.eventbus.EventBus;

@ApplicationScoped
public class CaseHubEngineImpl implements CaseHubEngine {

    @Inject
    EventBus eventBus;

    private Map<UUID, CaseMetaInfo> definitions = new ConcurrentHashMap<>();

    @Override
    public UUID submitCase(CaseDefinition definition) {
        StateContext stateContext = new StateContextImpl();
        definitions.put(definition.getUuid(), new CaseMetaInfo(definition, stateContext));
        eventBus.publish("casehub.case.submitted", definition);
        return definition.getUuid();
    }
}
