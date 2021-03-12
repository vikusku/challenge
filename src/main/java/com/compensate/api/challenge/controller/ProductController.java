package com.compensate.api.challenge.controller;

import com.compensate.api.challenge.ProductAssembler;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.resource.Product;
import com.compensate.api.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/api/v1/products", produces = { MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE })
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAssembler productAssembler;

    @Autowired
    private PagedResourcesAssembler<ProductEntity> pagedResourcesAssembler;

    @GetMapping(name = "get_all_products")
    public ResponseEntity<PagedModel<Product>> getAll(@PageableDefault Pageable pageRequest) {
        return ResponseEntity.ok(
                pagedResourcesAssembler.toModel(
                        productService.getAll(pageRequest), productAssembler));
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
