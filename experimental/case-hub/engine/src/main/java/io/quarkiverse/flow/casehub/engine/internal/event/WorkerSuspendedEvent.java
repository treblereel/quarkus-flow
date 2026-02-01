package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class WorkerSuspendedEvent extends WorkerEvent {

    private final String reason;

    public WorkerSuspendedEvent(UUID caseId, String workerName) {
        super(caseId, workerName);
        this.reason = null;
    }

    public WorkerSuspendedEvent(UUID caseId, String workerName, String reason) {
        super(caseId, workerName);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
