package io.quarkiverse.flow.casehub;

import static io.serverlessworkflow.fluent.func.dsl.FuncDSL.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkiverse.flow.Flow;
import io.serverlessworkflow.api.types.Workflow;
import io.serverlessworkflow.fluent.func.FuncWorkflowBuilder;

@ApplicationScoped
public class HelloWorkflow extends Flow {

    @Inject
    DrafterAgent drafterAgent;

    @Override
    public Workflow descriptor() {
        return FuncWorkflowBuilder.workflow("hello")
                .tasks(set("{ message: \"hello world!\" }"), agent("draft", drafterAgent::draft, String.class))
                .build();
    }
}
