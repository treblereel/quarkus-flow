package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class CaseFailedEvent extends CaseEvent {

    private final String errorMessage;
    private final Throwable cause;

    public CaseFailedEvent(UUID caseId, String errorMessage) {
        super(caseId);
        this.errorMessage = errorMessage;
        this.cause = null;
    }

    public CaseFailedEvent(UUID caseId, String errorMessage, Throwable cause) {
        super(caseId);
        this.errorMessage = errorMessage;
        this.cause = cause;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Throwable getCause() {
        return cause;
    }

    @Override
    public CaseEventType getCaseEventType() {
        return CaseEventType.CASE_EXECUTION_FAILED;
    }
}
