package io.quarkiverse.flow.casehub.api.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

@Entity
public class CaseDefinition extends PanacheEntity {

    private UUID uuid;
    private String namespace = "default";
    private String name;
    private String version = "1.0";
    private String title = "Untitled Case Definition";

    @Transient
    private Set<Worker> workers = new HashSet<>();

    public CaseDefinition() {
        this(UUID.randomUUID());
    }

    public CaseDefinition(UUID uuid) {
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
