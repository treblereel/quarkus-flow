package io.quarkiverse.flow.casehub.api.model;

import io.quarkiverse.flow.casehub.api.context.StateContext;

public interface Worker {

    void execute(StateContext context);
}
