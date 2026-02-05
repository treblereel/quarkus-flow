package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class WorkerRetryingEvent extends WorkerEvent {

    private final int attempt;
    private final Throwable lastError;

    public WorkerRetryingEvent(UUID caseId, String workerName, int attempt) {
        super(caseId, workerName);
        this.attempt = attempt;
        this.lastError = null;
    }

    public WorkerRetryingEvent(UUID caseId, String workerName, int attempt, Throwable lastError) {
        super(caseId, workerName);
        this.attempt = attempt;
        this.lastError = lastError;
    }

    public int getAttempt() {
        return attempt;
    }

    public Throwable getLastError() {
        return lastError;
    }

    @Override
    public CaseEventType getCaseEventType() {
        return CaseEventType.WORKER_TASK_RETRYING;
    }
}
