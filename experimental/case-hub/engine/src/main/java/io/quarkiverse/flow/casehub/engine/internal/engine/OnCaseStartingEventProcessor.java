package io.quarkiverse.flow.casehub.engine.internal.engine;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkiverse.flow.casehub.engine.internal.event.CaseStatus;
import io.quarkiverse.flow.casehub.engine.internal.history.CaseEventTracker;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class OnCaseStartingEventProcessor {

    @ConsumeEvent(value = "casehub.case.starting")
    @CaseEventTracker(status = CaseStatus.STARTING)
    @WithTransaction
    public Uni<UUID> onEvent(CaseMetaInfo metaInfo) {
        System.out.println("Case created with ID: " + metaInfo.getDefinition().getUuid());
        return Uni.createFrom().item(metaInfo.getDefinition().getUuid());
    }
}
