package io.quarkiverse.flow.casehub.api.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.util.Set;
import java.util.UUID;

@Entity
public class Case extends PanacheEntity {

  private UUID uuid;
  private String namespace;
  private String name;
  private String version;
  private String title;

  private Set<Worker> workers;

  public Case() {
    this(UUID.randomUUID());
  }

  public Case(UUID uuid) {
    this.uuid = uuid;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void addWorker(Worker worker) {
    this.workers.add(worker);
  }
}
