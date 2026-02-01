package io.quarkiverse.flow.casehub.api.context;

import java.util.HashMap;
import java.util.Map;

public class StateContext implements AgenticScope {

  private final Map<String, Object> states = new HashMap<>();

}
