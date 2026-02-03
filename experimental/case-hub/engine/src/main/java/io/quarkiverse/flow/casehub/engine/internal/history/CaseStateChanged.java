package io.quarkiverse.flow.casehub.engine.internal.history;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.flow.casehub.api.model.CaseDefinition;
import io.quarkiverse.flow.casehub.engine.internal.event.CaseStatus;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;

@Entity(name = "event")
public class CaseStateChanged extends PanacheEntity {

    @ManyToOne
    private CaseDefinition caseDefinition;

    private Instant timestamp;

    private CaseStatus status;

    private String reason;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode context;

    public CaseDefinition getCaseDefinition() {
        return caseDefinition;
    }

    public void setCaseDefinition(CaseDefinition caseDefinition) {
        this.caseDefinition = caseDefinition;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public JsonNode getContext() {
        return context;
    }

    public void setContext(JsonNode context) {
        this.context = context;
    }
}
