package io.quarkiverse.flow.casehub.engine.internal.event;

/**
 * Unified enum for all history event types.
 * Maps to Temporal.io's EventType concept.
 */
public enum CaseEventType {
    // Case execution lifecycle events
    CASE_EXECUTION_STARTED,
    CASE_EXECUTION_COMPLETED,
    CASE_EXECUTION_FAILED,
    CASE_EXECUTION_CANCELED,
    CASE_EXECUTION_TERMINATED,
    CASE_EXECUTION_TIMED_OUT,
    CASE_EXECUTION_CONTINUED_AS_NEW,

    // Worker task events
    WORKER_TASK_SCHEDULED,
    WORKER_TASK_STARTED,
    WORKER_TASK_COMPLETED,
    WORKER_TASK_FAILED,
    WORKER_TASK_CANCELED,
    WORKER_TASK_TIMED_OUT,
    WORKER_TASK_SUSPENDED,
    WORKER_TASK_RESUMED,
    WORKER_TASK_RETRYING,

    // Timer events
    TIMER_STARTED,
    TIMER_FIRED,
    TIMER_CANCELED,

    // Milestone events
    MILESTONE_REACHED,
    MILESTONE_FAILED
}
