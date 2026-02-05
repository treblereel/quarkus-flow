package io.quarkiverse.flow.casehub.engine.internal.event;

import java.time.Duration;
import java.util.UUID;

public class CaseTimedOutEvent extends CaseEvent {

    private final Duration timeout;

    public CaseTimedOutEvent(UUID caseId) {
        super(caseId);
        this.timeout = null;
    }

    public CaseTimedOutEvent(UUID caseId, Duration timeout) {
        super(caseId);
        this.timeout = timeout;
    }

    public Duration getTimeout() {
        return timeout;
    }

    @Override
    public CaseEventType getCaseEventType() {
        return CaseEventType.CASE_EXECUTION_TIMED_OUT;
    }
}
