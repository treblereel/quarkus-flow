package io.quarkiverse.flow.casehub.api.model;

import io.quarkiverse.flow.casehub.api.event.ContextChangedEvent;

public interface HasModelChangeListener {

  void onContextChangeEvent(ContextChangedEvent event);
}
