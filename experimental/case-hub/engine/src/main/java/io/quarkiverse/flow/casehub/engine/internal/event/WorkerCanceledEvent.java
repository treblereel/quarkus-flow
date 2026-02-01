package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class WorkerCanceledEvent extends WorkerEvent {

    private final String reason;

    public WorkerCanceledEvent(UUID caseId, String workerName) {
        super(caseId, workerName);
        this.reason = null;
    }

    public WorkerCanceledEvent(UUID caseId, String workerName, String reason) {
        super(caseId, workerName);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
