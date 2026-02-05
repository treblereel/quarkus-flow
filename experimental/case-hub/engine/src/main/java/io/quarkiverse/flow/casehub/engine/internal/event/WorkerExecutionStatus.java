package io.quarkiverse.flow.casehub.engine.internal.event;

/**
 * Status of a worker execution.
 * Maps to Temporal.io's PendingActivityInfo status concept.
 */
public enum WorkerExecutionStatus {
    SCHEDULED,
    RUNNING,
    COMPLETED,
    FAILED,
    CANCELED,
    TIMED_OUT,
    SUSPENDED
}
