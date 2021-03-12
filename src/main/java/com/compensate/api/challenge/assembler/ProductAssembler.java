package com.compensate.api.challenge.assembler;

import com.compensate.api.challenge.controller.ProductController;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.resource.Product;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ProductAssembler extends RepresentationModelAssemblerSupport<ProductEntity, Product> {

    public ProductAssembler() {
        super(ProductController.class, Product.class);
    }

    @Override
    public Product toModel(ProductEntity entity) {
        final Product product = new Product(
                entity.getId(),
                entity.getName(),
                entity.getProperties(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                linkTo(ProductController.class).slash(entity.getId()).withSelfRel()
        );

        return product;
    }
}
