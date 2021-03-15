package com.compensate.api.challenge.request;

import java.util.Map;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

  @NotEmpty(message = "name cannot be null or empty")
  private String name;
  private Map<String, Object> properties;
  private String parentId;
}
