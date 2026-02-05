package io.quarkiverse.flow.casehub.engine.internal.history;

import java.time.Instant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.hibernate.reactive.mutiny.Mutiny;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.flow.casehub.engine.internal.engine.CaseMetaInfo;
import io.quarkiverse.flow.casehub.engine.internal.event.CaseEventType;
import io.quarkiverse.flow.casehub.engine.internal.model.CaseExecution;
import io.quarkiverse.flow.casehub.engine.internal.model.CaseHistoryEvent;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class HistoryService {

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @WithTransaction
    public Uni<Void> persistCaseEvent(CaseEventType eventType, CaseMetaInfo caseMetaInfo, JsonNode attributes) {
        CaseExecution execution = caseMetaInfo.getExecution();

        System.out.printf("Persisting case event: caseId=%s, eventType=%s%n",
                execution.getCaseId(),
                eventType.name());

        System.out.println("ATTRIBUTES  " + (attributes != null ? attributes.toPrettyString() : "null") + " "
                + (attributes != null && attributes.isEmpty()));

        CaseHistoryEvent event = new CaseHistoryEvent();
        event.setEventId(execution.incrementHistoryLength());
        event.setEventType(eventType);
        event.setTimestamp(Instant.now());
        event.setReason("Case event: " + eventType.name().toLowerCase().replace('_', ' '));

        if (attributes != null && !attributes.isEmpty()) {
            event.setAttributes(attributes);
        }

        return sessionFactory.withTransaction(session -> session.merge(execution)
                .invoke(mergedExecution -> event.setCaseExecution(mergedExecution))
                .chain(() -> session.persist(event)))
                .replaceWithVoid();
    }
}
