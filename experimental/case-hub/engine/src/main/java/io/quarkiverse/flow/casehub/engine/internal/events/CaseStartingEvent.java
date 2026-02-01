package io.quarkiverse.flow.casehub.engine.internal.events;

import java.util.UUID;

/**
 * Event fired before a Case transitions to running state.
 * Can be used to validate preconditions or veto the start.
 */
public class CaseStartingEvent extends CaseEvent {

    public CaseStartingEvent(UUID caseId) {
        super(caseId);
    }
}
