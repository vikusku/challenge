package com.compensate.api.challenge.controller;

import com.compensate.api.challenge.ProductAssembler;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.request.ProductRequest;
import com.compensate.api.challenge.resource.Product;
import com.compensate.api.challenge.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductAssembler productAssembler;

    final UUID id = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");
    final String createdAt = "2020-04-29T12:50:08+00:00";
    final String modifiedAt = "2020-05-29T12:50:08+00:00";

    @Test
    public void getShouldReturnProductResourceForExistingProduct() throws Exception {
        final Product resource = new Product(
            id,
            "TestProduct",
            Maps.newLinkedHashMap(),
            LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            LocalDateTime.parse(modifiedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        );

        final Map<String, Object> supPropertiesMap = new LinkedHashMap<>();
        supPropertiesMap.put("subProp1", "some value");
        supPropertiesMap.put("subProp2", 12.3);
        supPropertiesMap.put("subProp3", false);
        supPropertiesMap.put("subProp4", Arrays.asList("one", "two", "three"));

        resource.add("prop1", "some value");
        resource.add("prop2", 12.3);
        resource.add("prop3", 1300399023);
        resource.add("prop4", false);
        resource.add("prop5", Arrays.asList("one", "two", "three"));
        resource.add("prop6", supPropertiesMap);

        when(productService.get(id)).thenReturn(Optional.of(resource));

        this.mockMvc
                .perform(get("/api/v1/products/" + id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(readFromFile("getProductResponse.json")));
    }

    @Test
    public void getShouldReturn404IfProductDoesNotExist() throws Exception {
        when(productService.get(id)).thenReturn(Optional.empty());

        this.mockMvc
                .perform(get("/api/v1/products/" + id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllShouldReturnCorrectRequestedPage() throws Exception {
        Pageable pageRequest = PageRequest.of(2, 4);
        Map<ProductEntity, Product> productsMap = getMockedProducts();
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
                .perform(get("/api/v1/products?page=2&size=4").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(readFromFile("getAllProductResponse.json")));
    }

    @Test
    public void createShouldSaveAndReturnNewProduct() throws Exception {
        final Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("prop1", "prop1 val");
        properties.put("prop2", false);
        properties.put("prop3", 12334);

        final ProductEntity productEntity = new ProductEntity(
                id,
                "TestProduct",
                properties,
                LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        );
        when(productService.create(any(ProductRequest.class))).thenReturn(productEntity);
        when(productAssembler.toModel(productEntity)).thenReturn(new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getProperties(),
                productEntity.getCreatedAt(),
                productEntity.getModifiedAt()
        ));

        this.mockMvc
                .perform(post("/api/v1/products")
                        .content(asJsonString(new ProductRequest("TestProduct", properties)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", equalTo("http://localhost/api/v1/products/" + id)))
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(readFromFile("createProductResponse.json")));
    }

    @Test
    public void updateShouldSaveAndUpdateExistingProduct() throws Exception {
        final ProductEntity productEntity = new ProductEntity(
                id,
                "TestProduct renamed",
                Maps.newLinkedHashMap(),
                LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                LocalDateTime.parse(modifiedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        );
        when(productService.update(eq(id), any(ProductRequest.class))).thenReturn(Optional.of(productEntity));
        when(productAssembler.toModel(productEntity)).thenReturn(new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getProperties(),
                productEntity.getCreatedAt(),
                productEntity.getModifiedAt()
        ));

        this.mockMvc
                .perform(put("/api/v1/products/" + id)
                        .content(asJsonString(new ProductRequest("TestProduct renamed", Maps.newLinkedHashMap())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(readFromFile("updateProductResponse.json")));
    }

    @Test
    public void updateShouldReturn404IfProductDoesNotExist() throws Exception {
        when(productService.update(eq(id), any(ProductRequest.class))).thenReturn(Optional.empty());

        this.mockMvc
                .perform(put("/api/v1/products/" + id)
                        .content(asJsonString(new ProductRequest("TestProduct renamed", Maps.newLinkedHashMap())))
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

    private Map<ProductEntity, Product> getMockedProducts() {
        final Map<ProductEntity, Product> products = new LinkedHashMap<>();
        final Map<String, Object> properties1 = new LinkedHashMap<>();
        properties1.put("prop1", "prop1 value");
        properties1.put("prop2", 12.3);
        properties1.put("prop3", false);
        properties1.put("prop4", Arrays.asList("one", "two", "three", "four"));

        products.put(
            new ProductEntity(
                UUID.fromString("257a3e82-59c9-47c9-880a-74a1bbef8a07"),
                "TestProduct1",
                properties1,
                LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                LocalDateTime.parse(modifiedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)),
            new Product(
                UUID.fromString("257a3e82-59c9-47c9-880a-74a1bbef8a07"),
                "TestProduct1",
                properties1,
                LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                LocalDateTime.parse(modifiedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)));

        products.put(
            new ProductEntity(
                UUID.fromString("3877ed4b-e2cb-4097-8c58-a8001c44096a"),
                "TestProduct2",
                Maps.newLinkedHashMap(),
                LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                LocalDateTime.parse(modifiedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            ),
            new Product(
                UUID.fromString("3877ed4b-e2cb-4097-8c58-a8001c44096a"),
                "TestProduct2",
                Maps.newLinkedHashMap(),
                LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                LocalDateTime.parse(modifiedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)));

        products.put(
            new ProductEntity(
                UUID.fromString("7a23ac61-178d-4f28-9b63-d977c629176d"),
                "TestProduct3",
                Maps.newLinkedHashMap(),
                LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                LocalDateTime.parse(modifiedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            ),
            new Product(
                UUID.fromString("7a23ac61-178d-4f28-9b63-d977c629176d"),
                "TestProduct3",
                Maps.newLinkedHashMap(),
                LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                LocalDateTime.parse(modifiedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)));

        final Map<String, Object> properties4 = new LinkedHashMap<>();
        final Map<String, String> subProperties4 = new LinkedHashMap<>();
        subProperties4.put("hello", "world");

        properties4.put("prop1", subProperties4);

        products.put(
            new ProductEntity(
                UUID.fromString("60ed9741-3c8a-4b9a-adf9-3c821ca2393a"),
                "TestProduct4",
                properties4,
                LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                LocalDateTime.parse(modifiedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            ),
            new Product(
                UUID.fromString("60ed9741-3c8a-4b9a-adf9-3c821ca2393a"),
                "TestProduct4",
                properties4,
                LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                LocalDateTime.parse(modifiedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)));

        return products;
    }
}