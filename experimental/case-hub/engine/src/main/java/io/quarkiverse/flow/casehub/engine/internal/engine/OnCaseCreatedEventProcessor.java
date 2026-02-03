package io.quarkiverse.flow.casehub.engine.internal.engine;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkiverse.flow.casehub.api.model.CaseDefinition;
import io.quarkiverse.flow.casehub.engine.internal.event.CaseStatus;
import io.quarkiverse.flow.casehub.engine.internal.history.CaseEventTracker;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class OnCaseCreatedEventProcessor {

    @ConsumeEvent(value = "casehub.case.created")
    @WithTransaction
    @CaseEventTracker(status = CaseStatus.CREATED)
    public Uni<UUID> onEvent(CaseMetaInfo metaInfo) {
        CaseDefinition definition = metaInfo.getDefinition();
        return definition.persist()
                .invoke(() -> System.out.println("Case created with ID: " + definition.getUuid()))
                .replaceWith(definition.getUuid());
    }
}
