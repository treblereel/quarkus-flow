package io.quarkiverse.flow.casehub.engine.internal.event;

import java.time.Instant;
import java.util.UUID;

public abstract class CaseEvent {

    private final UUID caseId;
    private final Instant timestamp;

    protected CaseEvent(UUID caseId) {
        this.caseId = caseId;
        this.timestamp = Instant.now();
    }

    public UUID getCaseId() {
        return caseId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the unified event type for this case event.
     */
    public abstract CaseEventType getCaseEventType();
}
