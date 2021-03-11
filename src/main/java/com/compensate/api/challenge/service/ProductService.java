package com.compensate.api.challenge.service;

import com.compensate.api.challenge.resource.Product;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    public List<Product> getAll(Integer pageNo, Integer pageSize) {
        return Lists.newArrayList();
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
