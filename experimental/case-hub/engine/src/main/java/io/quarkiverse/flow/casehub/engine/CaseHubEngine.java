package io.quarkiverse.flow.casehub.engine;

import java.util.UUID;

import io.quarkiverse.flow.casehub.api.model.CaseDefinition;

public interface CaseHubEngine {

    UUID submitCase(CaseDefinition definition);

}
