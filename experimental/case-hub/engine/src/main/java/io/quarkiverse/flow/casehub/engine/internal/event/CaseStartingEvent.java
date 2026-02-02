package io.quarkiverse.flow.casehub.engine.internal.event;

import io.quarkiverse.flow.casehub.api.model.CaseDefinition;

/**
 * Event fired before a Case transitions to running state.
 * Can be used to validate preconditions or veto the start.
 */
public class CaseStartingEvent extends CaseEvent {

    private CaseDefinition theCase;

    public CaseStartingEvent(CaseDefinition theCase) {
        super(theCase.getUuid());
        this.theCase = theCase;
    }

    public CaseDefinition getCase() {
        return theCase;
    }
}
