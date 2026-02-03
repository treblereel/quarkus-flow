package io.quarkiverse.flow.casehub.engine.internal.history;

import java.time.Instant;

import jakarta.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.flow.casehub.engine.internal.engine.CaseMetaInfo;
import io.quarkiverse.flow.casehub.engine.internal.event.CaseStatus;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class HistoryService {

    @WithTransaction
    public Uni<Void> persistCaseEvent(CaseStatus status, CaseMetaInfo caseMetaInfo, JsonNode diff) {
        System.out.printf("Persisting case event: caseId=%s, status=%s%n",
                caseMetaInfo.getDefinition().getUuid(),
                status.name());

        System.out.println("DIFF  " + diff.toPrettyString() + " " + diff.isEmpty());

        CaseStateChanged event = new CaseStateChanged();
        event.setCaseDefinition(caseMetaInfo.getDefinition());
        event.setTimestamp(Instant.now());
        event.setStatus(status);
        event.setReason("Case has been " + status.name().toLowerCase());
        if (!diff.isEmpty()) {
            event.setContext(caseMetaInfo.getContext().asJsonNode());
        }

        return event.persist().replaceWithVoid();
    }

}
