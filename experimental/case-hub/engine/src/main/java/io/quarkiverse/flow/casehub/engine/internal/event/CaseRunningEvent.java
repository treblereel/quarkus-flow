package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class CaseRunningEvent extends CaseEvent {

    public CaseRunningEvent(UUID caseId) {
        super(caseId);
    }
}
