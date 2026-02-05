package io.quarkiverse.flow.casehub.engine.internal.engine;

import io.quarkiverse.flow.casehub.api.context.StateContext;
import io.quarkiverse.flow.casehub.api.model.CaseDefinition;
import io.quarkiverse.flow.casehub.engine.internal.event.CaseExecutionStatus;
import io.quarkiverse.flow.casehub.engine.internal.model.CaseExecution;

public class CaseMetaInfo {

    private final CaseExecution execution;
    private final StateContext context;

    public CaseMetaInfo(CaseDefinition definition, StateContext context) {
        this.execution = new CaseExecution();
        this.execution.setCaseId(definition.getUuid());
        this.execution.setCaseDefinition(definition);
        this.execution.setTaskQueue(definition.getTaskQueue());
        this.context = context;
    }

    public CaseExecutionStatus getStatus() {
        return execution.getStatus();
    }

    public void setStatus(CaseExecutionStatus status) {
        execution.setStatus(status);
    }

    /**
     * Convenience method to get the case definition.
     */
    public CaseDefinition getDefinition() {
        return execution.getCaseDefinition();
    }

    /**
     * Returns the underlying case execution entity.
     */
    public CaseExecution getExecution() {
        return execution;
    }

    public StateContext getContext() {
        return context;
    }
}
