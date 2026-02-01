package io.quarkiverse.flow.casehub.engine;

import io.quarkiverse.flow.casehub.api.model.Case;

import java.util.UUID;

public interface CaseHubEngine {

    UUID submitCase(Case theCase);

}
