package io.quarkiverse.flow.casehub.engine.internal.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.flow.casehub.api.model.CaseDefinition;
import io.quarkiverse.flow.casehub.engine.internal.event.CaseExecutionStatus;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;

/**
 * JPA entity representing a running or completed instance of a CaseDefinition.
 * Maps to Temporal.io's WorkflowExecution concept.
 */
@Entity
public class CaseExecution extends PanacheEntity {

    /**
     * Stable business ID that survives continue-as-new operations.
     */
    private UUID caseId;

    /**
     * Unique identifier per execution attempt.
     */
    private UUID runId;

    /**
     * The case definition this execution is based on.
     */
    @ManyToOne
    private CaseDefinition caseDefinition;

    /**
     * Task queue for routing workers.
     */
    private String taskQueue = "default";

    /**
     * Current execution status.
     */
    @Enumerated(EnumType.STRING)
    private CaseExecutionStatus status = CaseExecutionStatus.RUNNING;

    /**
     * When the execution started.
     */
    private Instant startTime;

    /**
     * When the execution closed (completed, failed, canceled, etc.).
     */
    private Instant closeTime;

    /**
     * Total execution time.
     */
    private Instant executionTime;

    /**
     * Counter for history events.
     */
    private long historyLength = 0;

    /**
     * Memo - arbitrary user-defined metadata.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode memo;

    /**
     * Search attributes for visibility queries.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode searchAttributes;

    /**
     * Execution context/state.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode context;

    /**
     * Reference to previous run in continue-as-new chain.
     */
    private UUID previousRunId;

    /**
     * Reason for closing the execution.
     */
    private String closeReason;

    public CaseExecution() {
        this.runId = UUID.randomUUID();
        this.startTime = Instant.now();
    }

    public UUID getCaseId() {
        return caseId;
    }

    public void setCaseId(UUID caseId) {
        this.caseId = caseId;
    }

    public UUID getRunId() {
        return runId;
    }

    public void setRunId(UUID runId) {
        this.runId = runId;
    }

    public CaseDefinition getCaseDefinition() {
        return caseDefinition;
    }

    public void setCaseDefinition(CaseDefinition caseDefinition) {
        this.caseDefinition = caseDefinition;
    }

    public String getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(String taskQueue) {
        this.taskQueue = taskQueue;
    }

    public CaseExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(CaseExecutionStatus status) {
        this.status = status;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Instant closeTime) {
        this.closeTime = closeTime;
    }

    public Instant getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Instant executionTime) {
        this.executionTime = executionTime;
    }

    public long getHistoryLength() {
        return historyLength;
    }

    public void setHistoryLength(long historyLength) {
        this.historyLength = historyLength;
    }

    public long incrementHistoryLength() {
        return ++historyLength;
    }

    public JsonNode getMemo() {
        return memo;
    }

    public void setMemo(JsonNode memo) {
        this.memo = memo;
    }

    public JsonNode getSearchAttributes() {
        return searchAttributes;
    }

    public void setSearchAttributes(JsonNode searchAttributes) {
        this.searchAttributes = searchAttributes;
    }

    public JsonNode getContext() {
        return context;
    }

    public void setContext(JsonNode context) {
        this.context = context;
    }

    public UUID getPreviousRunId() {
        return previousRunId;
    }

    public void setPreviousRunId(UUID previousRunId) {
        this.previousRunId = previousRunId;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }
}
