package com.compensate.api.challenge.service;

import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.request.ProductRequest;
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

    public ProductEntity create(final ProductRequest productRequest) {
        return new ProductEntity();
    }

    public Optional<ProductEntity> update(final UUID id, final ProductRequest productRequest) {
        return Optional.of(new ProductEntity());
    }

    public Optional<ProductEntity> get(final UUID uuid) {
        return Optional.of(new ProductEntity());
    }

}
