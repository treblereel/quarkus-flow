package io.quarkiverse.flow.casehub.api.event;

public record ContextChangedEvent(String id, String path, String version) {

}
