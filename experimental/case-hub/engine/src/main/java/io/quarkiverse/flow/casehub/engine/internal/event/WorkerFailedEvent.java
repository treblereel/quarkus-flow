package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class WorkerFailedEvent extends WorkerEvent {

    private final String errorMessage;
    private final Throwable cause;

    public WorkerFailedEvent(UUID caseId, String workerName, String errorMessage) {
        super(caseId, workerName);
        this.errorMessage = errorMessage;
        this.cause = null;
    }

    public WorkerFailedEvent(UUID caseId, String workerName, String errorMessage, Throwable cause) {
        super(caseId, workerName);
        this.errorMessage = errorMessage;
        this.cause = cause;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Throwable getCause() {
        return cause;
    }
}
