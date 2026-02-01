package io.quarkiverse.flow.casehub;

import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.serverlessworkflow.impl.WorkflowApplication;

@ApplicationScoped
public class App {

    @Inject
    HelloWorkflow workflow;

    @PostConstruct
    public void init() {
        try (WorkflowApplication app = WorkflowApplication.builder().build()) {
            app.workflowDefinition(workflow.descriptor()).instance(Map.of()).start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
