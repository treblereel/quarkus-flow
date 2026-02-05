package io.quarkiverse.flow.casehub.engine.internal.event;

import java.time.Instant;
import java.util.UUID;

public abstract class WorkerEvent {

    private final UUID caseId;
    private final String workerName;
    private final Instant timestamp;

    protected WorkerEvent(UUID caseId, String workerName) {
        this.caseId = caseId;
        this.workerName = workerName;
        this.timestamp = Instant.now();
    }

    public UUID getCaseId() {
        return caseId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the unified event type for this worker event.
     */
    public abstract CaseEventType getCaseEventType();
}
