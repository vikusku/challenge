package com.compensate.api.challenge.controller;

import com.compensate.api.challenge.resource.Product;
import com.compensate.api.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/api/v1/products", produces = "application/json")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(name = "get_all_products")
    public ResponseEntity<List<Product>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        return ResponseEntity.ok(productService.getAll(pageNo, pageSize));
    }

    @PostMapping(name = "create_product", consumes = "application/json")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(UUID.randomUUID())
                .toUri();

        return ResponseEntity.created(uri).body(productService.create(product));
    }

    @PutMapping(path = "/{id}", name = "update_product", consumes = "application/json")
    public ResponseEntity<Product> update(@PathVariable UUID id, @RequestBody Product product) {
        return productService.update(id, product)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{id}", name = "get_product_by_id")
    public ResponseEntity<Product> get(@PathVariable final UUID id) {
        return productService.get(id)
                .map(product -> {
                    Link selfLink = linkTo(ProductController.class).slash(product.getId()).withSelfRel();
                    product.add(selfLink);

                    return ResponseEntity.ok(product);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
