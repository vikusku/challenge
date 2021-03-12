package com.compensate.api.challenge;

import com.compensate.api.challenge.assembler.ProductAssembler;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.resource.Product;
import com.google.common.collect.Maps;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import java.time.OffsetDateTime;
import java.util.UUID;

class ProductAssemblerTest {

    @Test
    public void testToModelAddsLinkToProductResource() {
        final ProductAssembler assembler = new ProductAssembler();

        final ProductEntity productEntity = new ProductEntity(
            UUID.fromString("3877ed4b-e2cb-4097-8c58-a8001c44096a"),
            "TestProduct",
            Maps.newLinkedHashMap(),
            OffsetDateTime.parse("2020-04-29T12:50:08+03:00"),
            OffsetDateTime.parse("2020-04-29T12:50:08+03:00")
        );

        final Product actualProduct = assembler.toModel(productEntity);
        final Product expectedProduct = new Product(
            UUID.fromString("3877ed4b-e2cb-4097-8c58-a8001c44096a"),
            "TestProduct",
            Maps.newLinkedHashMap(),
            OffsetDateTime.parse("2020-04-29T12:50:08+03:00"),
            OffsetDateTime.parse("2020-04-29T12:50:08+03:00"),
            Link.of("/api/v1/products/3877ed4b-e2cb-4097-8c58-a8001c44096a", "self")
        );

        Assertions.assertThat(actualProduct).isEqualTo(expectedProduct);
    }
}