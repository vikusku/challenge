package com.compensate.api.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    private UUID id;
    private String name;
    private Map<String, Object> properties;
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;
}
