package io.quarkiverse.flow.casehub.engine.internal.engine;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.vertx.core.eventbus.EventBus;

@ApplicationScoped
public class TransitionManager {

    @Inject
    EventBus eventBus;

}
