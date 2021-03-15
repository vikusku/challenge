package com.compensate.api.challenge;

import com.compensate.api.challenge.assembler.ProductAssembler;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.resource.Product;
import com.compensate.api.challenge.service.ProductService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductAssemblerTest {

    @MockBean
    private ProductService productService;

    @Test
    public void testToModelAddsLinkToProductResource() {
        final ProductAssembler assembler = new ProductAssembler(productService);

        final ProductEntity productEntity = new ProductEntity(
            UUID.fromString("3877ed4b-e2cb-4097-8c58-a8001c44096a"),
            "TestProduct",
            Maps.newLinkedHashMap(),
            OffsetDateTime.parse("2020-04-29T12:50:08+03:00"),
            OffsetDateTime.parse("2020-04-29T12:50:08+03:00"),
            null
        );

        final Product expectedProduct = new Product(
            UUID.fromString("3877ed4b-e2cb-4097-8c58-a8001c44096a"),
            "TestProduct",
            Maps.newLinkedHashMap(),
            OffsetDateTime.parse("2020-04-29T12:50:08+03:00"),
            OffsetDateTime.parse("2020-04-29T12:50:08+03:00"),
            Link.of("/api/v1/products/3877ed4b-e2cb-4097-8c58-a8001c44096a", "self")
        );

        final Product actualProduct = assembler.toModel(productEntity);

        assertThat(actualProduct).isEqualTo(expectedProduct);
    }

    @Test
    public void testToModelAddsParentAndChildrenLinksToProductResource() {
        final ProductAssembler assembler = new ProductAssembler(productService);

        final ProductEntity parentEntity = new ProductEntity();
        parentEntity.setId(UUID.fromString("a54e56fb-2cc9-4eb7-b952-c16802e8debc"));
        parentEntity.setName("Parent product");

        final ProductEntity productEntity = new ProductEntity(
                UUID.fromString("3877ed4b-e2cb-4097-8c58-a8001c44096a"),
                "Product",
                Maps.newLinkedHashMap(),
                OffsetDateTime.parse("2020-04-29T12:50:08+03:00"),
                OffsetDateTime.parse("2020-04-29T12:50:08+03:00"),
                parentEntity
        );

        when(productService.getRoot(productEntity)).thenReturn(parentEntity);
        when(productService.getChildren(productEntity)).thenReturn(Lists.newArrayList(
            new ProductEntity(
                    UUID.fromString("be8835b9-62ae-406f-be54-cd68f08dd1a4"),
                    "Child product",
                    Maps.newLinkedHashMap(),
                    OffsetDateTime.parse("2020-04-29T12:50:08+03:00"),
                    OffsetDateTime.parse("2020-04-29T12:50:08+03:00"),
                    productEntity)
        ));

        final Product actualProduct = assembler.toModel(productEntity);

        assertThat(actualProduct.getId()).isEqualTo(productEntity.getId());
        assertThat(actualProduct.getName()).isEqualTo(productEntity.getName());
        assertThat(actualProduct.getProperties()).isEqualTo(productEntity.getProperties());
        assertThat(actualProduct.getCreatedAt()).isEqualTo(productEntity.getCreatedAt());
        assertThat(actualProduct.getModifiedAt()).isEqualTo(productEntity.getModifiedAt());

        assertThat(actualProduct.getLink("self").get().toString())
                .isEqualTo(Link.of("/api/v1/products/" + productEntity.getId(), "self").toString());
        assertThat(actualProduct.getLink("parent").get().toString())
                .isEqualTo(Link.of("/api/v1/products/" + productEntity.getParent().getId(), "parent").toString());
        assertThat(actualProduct.getLink("root").get().toString())
                .isEqualTo(Link.of("/api/v1/products/" + productEntity.getParent().getId(), "root").toString());
        assertThat(actualProduct.getLink("subProducts").get().toString())
                .isEqualTo(Link.of("/api/v1/products/" + productEntity.getId() + "/subproducts?page=0&size=10", "subProducts").toString());
    }
}