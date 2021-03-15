package com.compensate.api.challenge.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.compensate.api.challenge.controller.ProductController;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.resource.Product;
import com.compensate.api.challenge.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductAssembler extends RepresentationModelAssemblerSupport<ProductEntity, Product> {

  @Autowired
  private ProductService productService;

  public ProductAssembler(final ProductService productService) {

    super(ProductController.class, Product.class);
    this.productService = productService;
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

    if (entity.getParent() != null) {
      product
          .add(linkTo(ProductController.class).slash(entity.getParent().getId()).withRel("parent"));
      product.add(linkTo(ProductController.class).slash(productService.getRoot(entity).getId())
          .withRel("root"));
    }

    final List<ProductEntity> subProducts = productService.getChildren(entity);
    if (!subProducts.isEmpty()) {
      product.add(linkTo(
          methodOn(ProductController.class).getAllSubProducts(entity.getId().toString(), 0, 10))
          .withRel("subProducts"));
    }

    return product;
  }
}
