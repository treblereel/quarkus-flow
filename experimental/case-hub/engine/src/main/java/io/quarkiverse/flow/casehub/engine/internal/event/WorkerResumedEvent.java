package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class WorkerResumedEvent extends WorkerEvent {

    public WorkerResumedEvent(UUID caseId, String workerName) {
        super(caseId, workerName);
    }
}
