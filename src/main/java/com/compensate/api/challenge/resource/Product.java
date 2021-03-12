package com.compensate.api.challenge.resource;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

// TODO Use OffsetDateTime instead LocalDateTime
@Getter
@JsonPropertyOrder({"_type", "id", "name", "properties", "created_at", "modified_at" })
public class Product extends RepresentationModel<Product> {
    @JsonProperty("_type")
    private String type;
    private UUID id;
    private String name;
    private Map<String, Object> properties;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("modified_at")
    private LocalDateTime modifiedAt;

    public Product(UUID id, String name, Map<String, Object> properties, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.type = this.getClass().getSimpleName().toLowerCase();
        this.id = id;
        this.name = name;
        this.properties = properties;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Product(String name) {
        this.type = this.getClass().getSimpleName().toLowerCase();
        this.id = UUID.randomUUID();
        this.name = name;
        this.properties = new LinkedHashMap<>();
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @JsonAnySetter
    public void add(String key, Object value) {
        properties.put(key, value);
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
