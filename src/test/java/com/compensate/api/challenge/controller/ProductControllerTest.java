package com.compensate.api.challenge.controller;

import com.compensate.api.challenge.resource.Product;
import com.compensate.api.challenge.service.ProductService;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    final UUID id = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");
    final String createdAt = "2020-04-29T12:50:08+00:00";
    final String modifiedAt = "2020-04-29T12:50:08+00:00";

    @Test
    public void getShouldReturnProductResourceForExistingProduct() throws Exception {
        final Product resource = new Product(
            id,
            "TestProduct",
            Maps.newLinkedHashMap(),
            LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            LocalDateTime.parse(modifiedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        );

        final Map<String, Object> supPropertiesMap = new HashMap<>();
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
                .andExpect(content().json(
                    readFromFile("getProductResponse.json")
                ));
    }

    @Test
    public void getShouldReturn404IfProductDoesNotExist() throws Exception {
        when(productService.get(id)).thenReturn(Optional.empty());

        this.mockMvc
                .perform(get("/api/v1/products/" + id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private String readFromFile(String fileName) throws Exception {
        String absolutePath = Paths.get("src","test","resources", "expectedResponse").toFile().getAbsolutePath();

        return new String(Files.readAllBytes(Paths.get(absolutePath + "/" + fileName)));
    }
}