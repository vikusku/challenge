package com.compensate.api.challenge.model;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity implements Node {

  private UUID id;
  private String name;
  private Map<String, Object> properties;
  private OffsetDateTime createdAt;
  private OffsetDateTime modifiedAt;
  private ProductEntity parent;
}
