package io.quarkiverse.flow.casehub.engine;

import java.util.UUID;

public interface CaseHubEngine {

    UUID start();

    void onCaseStartEvent();

}
