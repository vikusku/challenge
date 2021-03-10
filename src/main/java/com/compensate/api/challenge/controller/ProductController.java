package com.compensate.api.challenge.controller;

import com.compensate.api.challenge.resource.ProductResource;
import com.google.common.collect.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/products", produces = "application/json")
public class ProductController {

    @GetMapping(name = "get_all_products")
    public ResponseEntity<List<ProductResource>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        return ResponseEntity.ok(Lists.newArrayList());
    }

    @PostMapping(name = "create_product", consumes = "application/json")
    public ResponseEntity<ProductResource> create(final ProductResource productResource) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(UUID.randomUUID())
                .toUri();

        return ResponseEntity.created(uri).body(new ProductResource());
    }


    @PutMapping(name = "update_product", path = "{/id}", consumes = "application/json")
    public ResponseEntity<ProductResource> update(final ProductResource productResource) {
        return ResponseEntity.ok(new ProductResource());
    }

    @GetMapping(name = "get_product_by_id", path = "{/id}")
    public ResponseEntity<ProductResource> get(final UUID id) {
        return ResponseEntity.ok(new ProductResource());
    }
}
