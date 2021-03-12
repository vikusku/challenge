package com.compensate.api.challenge.dao;

import com.compensate.api.challenge.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductDao {
    ProductEntity insert(ProductEntity product);

    Page<ProductEntity> selectAll(Pageable pageRequest);

    Optional<ProductEntity> selectById(UUID id);

    Optional<ProductEntity> updateById(UUID id, ProductEntity product);
}
