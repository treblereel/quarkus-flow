package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class WorkerScheduledEvent extends WorkerEvent {

    public WorkerScheduledEvent(UUID caseId, String workerName) {
        super(caseId, workerName);
    }
}
