package io.quarkiverse.flow.casehub.engine.internal.event;

import java.util.UUID;

public class WorkerCompletedEvent extends WorkerEvent {

    private final Object result;

    public WorkerCompletedEvent(UUID caseId, String workerName) {
        super(caseId, workerName);
        this.result = null;
    }

    public WorkerCompletedEvent(UUID caseId, String workerName, Object result) {
        super(caseId, workerName);
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public CaseEventType getCaseEventType() {
        return CaseEventType.WORKER_TASK_COMPLETED;
    }
}
