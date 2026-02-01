package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class WorkerStartedEvent extends WorkerEvent {

    public WorkerStartedEvent(UUID caseId, String workerName) {
        super(caseId, workerName);
    }
}
