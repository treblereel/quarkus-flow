package io.quarkiverse.flow.casehub.engine.internal.engine;

import io.quarkiverse.flow.casehub.api.context.StateContext;
import io.quarkiverse.flow.casehub.api.model.CaseDefinition;
import io.quarkiverse.flow.casehub.engine.internal.event.CaseStatus;

public class CaseMetaInfo {

    private final CaseDefinition definition;
    private final StateContext context;

    private CaseStatus status = CaseStatus.CREATED;

    public CaseMetaInfo(CaseDefinition definition, StateContext context) {
        this.definition = definition;
        this.context = context;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public CaseDefinition getDefinition() {
        return definition;
    }

    public StateContext getContext() {
        return context;
    }
}
