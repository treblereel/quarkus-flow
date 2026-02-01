package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class CaseContinuedAsNewEvent extends CaseEvent {

    private final UUID newCaseId;

    public CaseContinuedAsNewEvent(UUID caseId) {
        super(caseId);
        this.newCaseId = null;
    }

    public CaseContinuedAsNewEvent(UUID caseId, UUID newCaseId) {
        super(caseId);
        this.newCaseId = newCaseId;
    }

    public UUID getNewCaseId() {
        return newCaseId;
    }
}
