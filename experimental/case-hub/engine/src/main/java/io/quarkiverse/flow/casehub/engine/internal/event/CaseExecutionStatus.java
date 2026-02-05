package io.quarkiverse.flow.casehub.engine.internal.event;

/**
 * Execution status of a case.
 * Maps to Temporal.io's ExecutionStatus concept.
 */
public enum CaseExecutionStatus {
    RUNNING,
    COMPLETED,
    FAILED,
    CANCELED,
    TERMINATED,
    CONTINUED_AS_NEW,
    TIMED_OUT
}
