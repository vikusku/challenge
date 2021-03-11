package com.compensate.api.challenge.service;

import com.compensate.api.challenge.resource.ProductResource;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    public List<ProductResource> getAll(Integer pageNo, Integer pageSize) {
        return Lists.newArrayList();
    }

    public ProductResource create(final ProductResource productDTO) {
        return new ProductResource();
    }

    public Optional<ProductResource> update(final UUID id, final ProductResource productDTO) {
        return Optional.of(new ProductResource());
    }

    public Optional<ProductResource> get(final UUID uuid) {
        return Optional.of(new ProductResource());
    }

}
