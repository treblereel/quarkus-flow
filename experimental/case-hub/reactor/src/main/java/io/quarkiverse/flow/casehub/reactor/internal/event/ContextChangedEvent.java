package io.quarkiverse.flow.casehub.reactor.internal.event;

public record ContextChangedEvent(String id, String path, String version) {

}
