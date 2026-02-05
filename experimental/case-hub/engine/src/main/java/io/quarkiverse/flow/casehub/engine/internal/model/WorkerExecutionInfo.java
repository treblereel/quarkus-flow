package io.quarkiverse.flow.casehub.engine.internal.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.flow.casehub.engine.internal.event.WorkerExecutionStatus;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;

/**
 * JPA entity for per-worker-invocation tracking.
 * Maps to Temporal.io's PendingActivityInfo concept.
 */
@Entity
public class WorkerExecutionInfo extends PanacheEntity {

    /**
     * Unique identifier for this worker execution.
     */
    private String workerId;

    /**
     * Name of the worker.
     */
    private String workerName;

    /**
     * The case execution this worker belongs to.
     */
    @ManyToOne
    private CaseExecution caseExecution;

    /**
     * Current status of the worker execution.
     */
    @Enumerated(EnumType.STRING)
    private WorkerExecutionStatus status = WorkerExecutionStatus.SCHEDULED;

    /**
     * When the worker was scheduled.
     */
    private Instant scheduledTime;

    /**
     * When the worker started executing.
     */
    private Instant startedTime;

    /**
     * When the worker closed (completed, failed, etc.).
     */
    private Instant closedTime;

    /**
     * Current attempt number (1-based).
     */
    private int attempt = 1;

    /**
     * Maximum retry attempts.
     */
    private int maxAttempts = 1;

    /**
     * Timeout in milliseconds.
     */
    private long timeoutMillis;

    /**
     * Input data for the worker.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode input;

    /**
     * Result from the worker execution.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode result;

    /**
     * Failure message if the worker failed.
     */
    private String failureMessage;

    /**
     * Type of failure if the worker failed.
     */
    private String failureType;

    public WorkerExecutionInfo() {
        this.scheduledTime = Instant.now();
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public CaseExecution getCaseExecution() {
        return caseExecution;
    }

    public void setCaseExecution(CaseExecution caseExecution) {
        this.caseExecution = caseExecution;
    }

    public WorkerExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(WorkerExecutionStatus status) {
        this.status = status;
    }

    public Instant getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Instant scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Instant getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Instant startedTime) {
        this.startedTime = startedTime;
    }

    public Instant getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(Instant closedTime) {
        this.closedTime = closedTime;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public JsonNode getInput() {
        return input;
    }

    public void setInput(JsonNode input) {
        this.input = input;
    }

    public JsonNode getResult() {
        return result;
    }

    public void setResult(JsonNode result) {
        this.result = result;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getFailureType() {
        return failureType;
    }

    public void setFailureType(String failureType) {
        this.failureType = failureType;
    }
}
