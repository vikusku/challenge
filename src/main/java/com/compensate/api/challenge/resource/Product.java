package com.compensate.api.challenge.resource;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

@Getter
@JsonPropertyOrder({"_type", "id", "name", "properties", "created_at", "modified_at", "_links"})
public class Product extends RepresentationModel<Product> {

  @JsonProperty("_type")
  private String type;
  private UUID id;
  private String name;
  private Map<String, Object> properties;
  @JsonProperty("created_at")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxx")
  private OffsetDateTime createdAt;
  @JsonProperty("modified_at")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxx")
  private OffsetDateTime modifiedAt;

  public Product(
      UUID id,
      String name,
      Map<String, Object> properties,
      OffsetDateTime createdAt,
      OffsetDateTime modifiedAt,
      Link link) {
    this.type = this.getClass().getSimpleName().toLowerCase();
    this.id = id;
    this.name = name;
    this.properties = properties;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
    this.add(link);
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

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setModifiedAt(OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }
}
