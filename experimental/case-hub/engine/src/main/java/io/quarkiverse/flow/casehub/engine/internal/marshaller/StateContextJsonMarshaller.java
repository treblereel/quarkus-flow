package io.quarkiverse.flow.casehub.engine.internal.marshaller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fabric8.zjsonpatch.JsonDiff;
import io.quarkiverse.flow.casehub.api.context.StateContext;
import io.quarkiverse.flow.casehub.engine.internal.context.StateContextImpl;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class StateContextJsonMarshaller {

    private static final ObjectMapper mapper = new ObjectMapper();


    String toJson(StateContext stateContext) {
        try {
            return mapper.writeValueAsString(stateContext.getData());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    StateContext fromJson(String json) {
        Map<String, Object> map = mapper.convertValue(json, Map.class);
        return new StateContextImpl(map);
    }

    String getDiff(StateContext context1, StateContext context2) {
        String json1 = toJson(context1);
        String json2 = toJson(context2);
        try {
            JsonNode asNode1 = mapper.readTree(json1);
            JsonNode asNode2 = mapper.readTree(json2);
            JsonNode patch = JsonDiff.asJson(asNode1, asNode2);
            System.out.println("PATCH:\n" + patch.toPrettyString());
            return patch.asText();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
