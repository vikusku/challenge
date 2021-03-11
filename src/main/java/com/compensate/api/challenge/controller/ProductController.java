package com.compensate.api.challenge.controller;

import com.compensate.api.challenge.resource.ProductResource;
import com.compensate.api.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/products", produces = "application/json")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(name = "get_all_products")
    public ResponseEntity<List<ProductResource>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        return ResponseEntity.ok(productService.getAll(pageNo, pageSize));
    }

    @PostMapping(name = "create_product", consumes = "application/json")
    public ResponseEntity<ProductResource> create(final ProductResource productResource) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(UUID.randomUUID())
                .toUri();

        return ResponseEntity.created(uri).body(new ProductResource());
    }

    @PutMapping(path = "/{id}", name = "update_product", consumes = "application/json")
    public ResponseEntity<ProductResource> update(@PathVariable UUID id, final ProductResource productResource) {
        return productService.update(id, productResource)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{id}", name = "get_product_by_id")
    public ResponseEntity<ProductResource> get(final UUID id) {
        return productService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
