package io.quarkiverse.flow.casehub;

import io.serverlessworkflow.fluent.func.FuncWorkflowBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkiverse.flow.Flow;
import io.serverlessworkflow.api.types.Workflow;
import jakarta.inject.Inject;

import static io.serverlessworkflow.fluent.func.dsl.FuncDSL.*;

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
