package com.compensate.api.challenge.service;

import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.resource.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    public Page<ProductEntity> getAll(Pageable pageRequest) {
        return Page.empty();
    }

    public Product create(final Product productDTO) {
        return new Product("tmp");
    }

    public Optional<Product> update(final UUID id, final Product productDTO) {
        return Optional.of(new Product("tmp"));
    }

    public Optional<Product> get(final UUID uuid) {
        return Optional.of(new Product("tmp"));
    }

}
