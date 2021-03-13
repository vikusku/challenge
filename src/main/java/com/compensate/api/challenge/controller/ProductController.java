package com.compensate.api.challenge.controller;

import com.compensate.api.challenge.assembler.ProductAssembler;
import com.compensate.api.challenge.exception.InvalidIdException;
import com.compensate.api.challenge.exception.ProductNotFoundException;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.request.ProductRequest;
import com.compensate.api.challenge.resource.Product;
import com.compensate.api.challenge.service.ProductService;
import com.compensate.api.challenge.util.ExampleSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/products", produces = { MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAssembler productAssembler;

    @Autowired
    private PagedResourcesAssembler<ProductEntity> pagedResourcesAssembler;

    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found products",
                    content = { @Content(
                            schema = @Schema(implementation = PagedModel.class),
                            examples = { @ExampleObject (value = ExampleSchema.ALL_PRODUCTS)})}),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content)})
    @GetMapping(name = "get_all_products")
    public ResponseEntity<PagedModel<Product>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(
                pagedResourcesAssembler.toModel(
                        productService.getAll(PageRequest.of(page, size)), productAssembler));
    }

    @Operation(summary = "Create product")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product was created successfully",
                    headers = @Header(name = "Location", description = "Location of the newly created product."),
                    content = { @Content(
                            schema = @Schema(implementation = Product.class),
                            examples = { @ExampleObject (value = ExampleSchema.PRODUCT)})}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body",
                    content = @Content),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content)})
    @PostMapping(name = "create_product", consumes = "application/json")
    public ResponseEntity<Product> create(
            @Parameter(
                    description="Product to create.",
                    schema = @Schema(implementation = ProductRequest.class))
            @Valid @NotNull @RequestBody
            final ProductRequest productRequest) {
        final Product product = productAssembler.toModel(productService.create(productRequest));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(uri).body(product);
    }

    @Operation(summary = "Update a product by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was updated successfully",
                    content = { @Content(
                            schema = @Schema(implementation = Product.class),
                            examples = { @ExampleObject (value = ExampleSchema.PRODUCT)})}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body or invalid id supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content)})
    @PutMapping(path = "/{id}", name = "update_product", consumes = "application/json")
    public ResponseEntity<Product> update(
            @Parameter(description = "UUID id of product to be updated")
            @PathVariable final String id,
            @Parameter(
                    description="Product to update.",
                    schema = @Schema(implementation = ProductRequest.class))
            @Valid @NotNull @RequestBody
            final ProductRequest productRequest) {

        try {
            final UUID uuid = UUID.fromString(id);
            return productService.update(uuid, productRequest)
                    .map(entity -> ResponseEntity.ok(productAssembler.toModel(entity)))
                    .orElseThrow(() -> new ProductNotFoundException(String.format("product with id [%s] does not exist", id)));
        } catch (final IllegalArgumentException ex) {
            throw new InvalidIdException(String.format("%s is not valid", id));
        }
    }

    @Operation(summary = "Get a product by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the product",
                    content = { @Content(
                            schema = @Schema(implementation = Product.class),
                            examples = { @ExampleObject (value = ExampleSchema.PRODUCT)})}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content)})
    @GetMapping(path = "/{id}", name = "get_product_by_id")
    public ResponseEntity<Product> get(
            @Parameter(description = "UUID id of product to be searched")
            @PathVariable final String id) {
        try {
            final UUID uuid = UUID.fromString(id);

            return productService.get(uuid)
                    .map(entity -> ResponseEntity.ok(productAssembler.toModel(entity)))
                    .orElseThrow(() ->
                            new ProductNotFoundException(String.format("product with id [%s] does not exist", id)));
        } catch (final IllegalArgumentException ex) {
            throw new InvalidIdException(String.format("%s is not valid", id));
        }
    }
}
