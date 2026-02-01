package io.quarkiverse.flow.casehub.engine.internal.event;

import java.time.Duration;
import java.util.UUID;

public class WorkerTimedOutEvent extends WorkerEvent {

    private final Duration timeout;

    public WorkerTimedOutEvent(UUID caseId, String workerName) {
        super(caseId, workerName);
        this.timeout = null;
    }

    public WorkerTimedOutEvent(UUID caseId, String workerName, Duration timeout) {
        super(caseId, workerName);
        this.timeout = timeout;
    }

    public Duration getTimeout() {
        return timeout;
    }
}
