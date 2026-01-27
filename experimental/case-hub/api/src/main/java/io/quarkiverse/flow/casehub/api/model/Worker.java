package io.quarkiverse.flow.casehub.api.model;

import io.serverlessworkflow.api.types.Workflow;

import java.util.function.Predicate;

public record Worker(Predicate<String> runIf, Workflow workflow){

}
