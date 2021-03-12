package com.compensate.api.challenge.controller;

import com.compensate.api.challenge.assembler.ProductAssembler;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.request.ProductRequest;
import com.compensate.api.challenge.resource.Product;
import com.compensate.api.challenge.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.*;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO test that request body is validated
// TODO test UpdateProductException
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductAssembler productAssembler;

    @Test
    public void getShouldReturnProductResourceForExistingProduct() throws Exception {
        final UUID id = UUID.fromString("257a3e82-59c9-47c9-880a-74a1bbef8a07");
        final String createdAt = "2020-04-29T12:50:08+00:00";
        final String modifiedAt = "2020-05-29T12:50:08+00:00";

        final Map<String, Object> supPropertiesMap = new LinkedHashMap<>();
        supPropertiesMap.put("subProp1", "some value");
        supPropertiesMap.put("subProp2", 12.3);
        supPropertiesMap.put("subProp3", false);
        supPropertiesMap.put("subProp4", Arrays.asList("one", "two", "three"));

        final Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("prop1", "some value");
        properties.put("prop2", 12.3);
        properties.put("prop3", 1300399023);
        properties.put("prop4", false);
        properties.put("prop5", Arrays.asList("one", "two", "three"));
        properties.put("prop6", supPropertiesMap);

        final ProductEntity entity = new ProductEntity(
                id,
                "Test Product",
                properties,
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(modifiedAt)
        );
        final Product product = new Product(
                entity.getId(),
                entity.getName(),
                entity.getProperties(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                Link.of("http://localhost/api/v1/products/257a3e82-59c9-47c9-880a-74a1bbef8a07")
        );

        when(productService.get(id)).thenReturn(Optional.of(entity));
        when(productAssembler.toModel(entity)).thenReturn(product);

        this.mockMvc
                .perform(get("/api/v1/products/" + id).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(readFromFile("getProductResponse.json")));
    }

    @Test
    public void getShouldReturn404IfProductDoesNotExist() throws Exception {
        final UUID id = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");
        when(productService.get(id)).thenReturn(Optional.empty());

        this.mockMvc
                .perform(get("/api/v1/products/" + id).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllShouldReturnCorrectRequestedPage() throws Exception {
        Pageable pageRequest = PageRequest.of(2, 4);
        Map<ProductEntity, Product> productsMap = getAllRequestMockedProducts();
        List<ProductEntity> productEntities = Lists.newArrayList(productsMap.keySet());
        final ProductEntity pe1 = productEntities.get(0);
        final ProductEntity pe2 = productEntities.get(1);
        final ProductEntity pe3 = productEntities.get(2);
        final ProductEntity pe4 = productEntities.get(3);

        when(productService.getAll(any(Pageable.class))).thenReturn(new PageImpl<>(productEntities, pageRequest, 20));
        when(productAssembler.toModel(pe1)).thenReturn(productsMap.get(pe1));
        when(productAssembler.toModel(pe2)).thenReturn(productsMap.get(pe2));
        when(productAssembler.toModel(pe3)).thenReturn(productsMap.get(pe3));
        when(productAssembler.toModel(pe4)).thenReturn(productsMap.get(pe4));

        this.mockMvc
                .perform(get("/api/v1/products?page=2&size=4").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(readFromFile("getAllProductResponse.json")));
    }

    @Test
    public void createShouldSaveAndReturnNewProduct() throws Exception {
        final UUID id = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");
        final String createdAt = "2020-04-29T12:50:08+02:00";

        final Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("prop1", "prop1 val");
        properties.put("prop2", false);
        properties.put("prop3", 12334);

        final ProductEntity productEntity = new ProductEntity(
                id,
                "TestProduct",
                properties,
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(createdAt)
        );
        when(productService.create(any(ProductRequest.class))).thenReturn(productEntity);

        final Product product = new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getProperties(),
                productEntity.getCreatedAt(),
                productEntity.getModifiedAt(),
                Link.of("http://localhost/api/v1/products/d56b4377-e906-4c63-955c-70dbb1d919b2")
        );

        when(productAssembler.toModel(productEntity)).thenReturn(product);

        this.mockMvc
                .perform(post("/api/v1/products")
                        .content(asJsonString(new ProductRequest("TestProduct", properties)))
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", equalTo("http://localhost/api/v1/products/" + id)))
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(readFromFile("createProductResponse.json")));
    }

    @Test
    public void updateShouldSaveAndUpdateExistingProduct() throws Exception {
        final UUID id = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");
        final String createdAt = "2020-04-29T12:50:08+03:00";
        final String modifiedAt = "2020-05-29T12:50:08+03:00";

        final ProductEntity productEntity = new ProductEntity(
                id,
                "TestProduct renamed",
                Maps.newLinkedHashMap(),
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(modifiedAt)
        );
        when(productService.update(eq(id), any(ProductRequest.class))).thenReturn(Optional.of(productEntity));

        final Product product = new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getProperties(),
                productEntity.getCreatedAt(),
                productEntity.getModifiedAt(),
                Link.of("http://localhost/api/v1/products/d56b4377-e906-4c63-955c-70dbb1d919b2")
        );

        when(productAssembler.toModel(productEntity)).thenReturn(product);

        this.mockMvc
                .perform(put("/api/v1/products/" + id)
                        .content(asJsonString(new ProductRequest("TestProduct renamed", Maps.newLinkedHashMap())))
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(readFromFile("updateProductResponse.json")));
    }

    @Test
    public void updateShouldReturn404IfProductDoesNotExist() throws Exception {
        final UUID id = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");
        when(productService.update(eq(id), any(ProductRequest.class))).thenReturn(Optional.empty());

        this.mockMvc
                .perform(put("/api/v1/products/" + id)
                        .content(asJsonString(new ProductRequest("TestProduct renamed", Maps.newLinkedHashMap())))
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private String readFromFile(String fileName) throws Exception {
        String absolutePath = Paths.get("src","test","resources", "expectedResponse").toFile().getAbsolutePath();

        return new String(Files.readAllBytes(Paths.get(absolutePath + "/" + fileName)));
    }

    private String asJsonString(final ProductRequest productRequest) {
        try {
            return new ObjectMapper().writeValueAsString(productRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<ProductEntity, Product> getAllRequestMockedProducts() {
        final String createdAt = "2020-04-29T12:50:08+00:00";
        final String modifiedAt = "2020-05-29T12:50:08+00:00";

        final Map<ProductEntity, Product> products = new LinkedHashMap<>();

        final Map<String, Object> supPropertiesMap = new LinkedHashMap<>();
        supPropertiesMap.put("subProp1", "some value");
        supPropertiesMap.put("subProp2", 12.3);
        supPropertiesMap.put("subProp3", false);
        supPropertiesMap.put("subProp4", Arrays.asList("one", "two", "three"));

        final Map<String, Object> properties1 = new LinkedHashMap<>();
        properties1.put("prop1", "some value");
        properties1.put("prop2", 12.3);
        properties1.put("prop3", 1300399023);
        properties1.put("prop4", false);
        properties1.put("prop5", Arrays.asList("one", "two", "three"));
        properties1.put("prop6", supPropertiesMap);

        products.put(
            new ProductEntity(
                UUID.fromString("257a3e82-59c9-47c9-880a-74a1bbef8a07"),
                "TestProduct1",
                properties1,
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(modifiedAt)),
            new Product(
                UUID.fromString("257a3e82-59c9-47c9-880a-74a1bbef8a07"),
                "TestProduct1",
                properties1,
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(modifiedAt),
                Link.of("http://localhost/api/v1/products/257a3e82-59c9-47c9-880a-74a1bbef8a07")));

        products.put(
            new ProductEntity(
                UUID.fromString("3877ed4b-e2cb-4097-8c58-a8001c44096a"),
                "TestProduct2",
                Maps.newLinkedHashMap(),
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(modifiedAt)
            ),
            new Product(
                UUID.fromString("3877ed4b-e2cb-4097-8c58-a8001c44096a"),
                "TestProduct2",
                Maps.newLinkedHashMap(),
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(modifiedAt),
                Link.of("http://localhost/api/v1/products/3877ed4b-e2cb-4097-8c58-a8001c44096a")));

        products.put(
            new ProductEntity(
                UUID.fromString("7a23ac61-178d-4f28-9b63-d977c629176d"),
                "TestProduct3",
                Maps.newLinkedHashMap(),
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(modifiedAt)
            ),
            new Product(
                UUID.fromString("7a23ac61-178d-4f28-9b63-d977c629176d"),
                "TestProduct3",
                Maps.newLinkedHashMap(),
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(modifiedAt),
                Link.of("http://localhost/api/v1/products/7a23ac61-178d-4f28-9b63-d977c629176d")));

        final Map<String, Object> properties4 = new LinkedHashMap<>();
        final Map<String, String> subProperties4 = new LinkedHashMap<>();
        subProperties4.put("hello", "world");

        properties4.put("prop1", subProperties4);

        products.put(
            new ProductEntity(
                UUID.fromString("60ed9741-3c8a-4b9a-adf9-3c821ca2393a"),
                "TestProduct4",
                properties4,
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(modifiedAt)
            ),
            new Product(
                UUID.fromString("60ed9741-3c8a-4b9a-adf9-3c821ca2393a"),
                "TestProduct4",
                properties4,
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(modifiedAt),
                Link.of("http://localhost/api/v1/products/60ed9741-3c8a-4b9a-adf9-3c821ca2393a")));

        return products;
    }
}