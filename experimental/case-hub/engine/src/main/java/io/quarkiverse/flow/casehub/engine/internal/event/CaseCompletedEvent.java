package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class CaseCompletedEvent extends CaseEvent {

    private final Object result;

    public CaseCompletedEvent(UUID caseId) {
        super(caseId);
        this.result = null;
    }

    public CaseCompletedEvent(UUID caseId, Object result) {
        super(caseId);
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public CaseEventType getCaseEventType() {
        return CaseEventType.CASE_EXECUTION_COMPLETED;
    }
}
