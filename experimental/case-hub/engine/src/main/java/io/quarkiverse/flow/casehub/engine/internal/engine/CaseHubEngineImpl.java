package io.quarkiverse.flow.casehub.engine.internal.engine;

import io.quarkiverse.flow.casehub.api.model.Case;
import io.quarkiverse.flow.casehub.engine.CaseHubEngine;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class CaseHubEngineImpl implements CaseHubEngine {

  @Inject
  EventBus eventBus;

  @Override
  public UUID submitCase(Case theCase) {
    eventBus.publish("casehub.case.submit", theCase);
    return theCase.getUuid();
  }
}
