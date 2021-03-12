package com.compensate.api.challenge.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotEmpty(message = "Name cannot be null")
    private String name;
    private Map<String, Object> properties;
}
