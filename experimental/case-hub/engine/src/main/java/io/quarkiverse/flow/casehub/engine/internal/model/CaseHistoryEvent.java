package io.quarkiverse.flow.casehub.engine.internal.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.flow.casehub.engine.internal.event.CaseEventType;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;

/**
 * JPA entity representing an append-only ordered event log entry.
 * Replaces CaseStateChanged with a unified event model.
 * Maps to Temporal.io's HistoryEvent concept.
 */
@Entity
@Table(name = "case_history_event")
public class CaseHistoryEvent extends PanacheEntity {

    /**
     * Sequential event ID within the execution.
     */
    private long eventId;

    /**
     * The case execution this event belongs to.
     */
    @ManyToOne
    private CaseExecution caseExecution;

    /**
     * Type of event (unified enum).
     */
    @Enumerated(EnumType.STRING)
    private CaseEventType eventType;

    /**
     * When the event occurred.
     */
    private Instant timestamp;

    /**
     * Event-specific payload (JSONB).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode attributes;

    /**
     * Worker name (nullable, for worker events).
     */
    private String workerName;

    /**
     * Worker ID (nullable, for worker events).
     */
    private String workerId;

    /**
     * Reason for the event.
     */
    private String reason;

    public CaseHistoryEvent() {
        this.timestamp = Instant.now();
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public CaseExecution getCaseExecution() {
        return caseExecution;
    }

    public void setCaseExecution(CaseExecution caseExecution) {
        this.caseExecution = caseExecution;
    }

    public CaseEventType getEventType() {
        return eventType;
    }

    public void setEventType(CaseEventType eventType) {
        this.eventType = eventType;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public JsonNode getAttributes() {
        return attributes;
    }

    public void setAttributes(JsonNode attributes) {
        this.attributes = attributes;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
