package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class CaseCanceledEvent extends CaseEvent {

    private final String reason;

    public CaseCanceledEvent(UUID caseId) {
        super(caseId);
        this.reason = null;
    }

    public CaseCanceledEvent(UUID caseId, String reason) {
        super(caseId);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public CaseEventType getCaseEventType() {
        return CaseEventType.CASE_EXECUTION_CANCELED;
    }
}
