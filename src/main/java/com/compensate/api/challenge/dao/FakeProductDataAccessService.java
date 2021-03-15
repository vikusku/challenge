package com.compensate.api.challenge.dao;

import com.compensate.api.challenge.exception.UpdateProductException;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.util.PaginatedResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakeProductDataAccessService implements ProductDao {

    @Autowired
    private PaginatedResponseUtil paginatedResponseUtil;

    private static List<ProductEntity> DB = new ArrayList<>();

    @Override
    public ProductEntity insert(ProductEntity product) {
        final ProductEntity saved = new ProductEntity(
                UUID.randomUUID(),
                product.getName(),
                product.getProperties(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                product.getParent());


        DB.add(saved);
        return saved;
    }

    @Override
    public Page<ProductEntity> selectAll(Pageable pageable) {
        return paginatedResponseUtil.getPage(DB, pageable);
    }

    @Override
    public List<ProductEntity> selectAll() {
        return DB;
    }

    @Override
    public Optional<ProductEntity> selectById(UUID id) {

        return DB.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<ProductEntity> updateById(UUID id, ProductEntity updatedProduct) {
        return selectById(id).map(product -> {
            int indexOfProduct = DB.indexOf(product);
            if (indexOfProduct >= 0) {
                final ProductEntity saved = new ProductEntity(
                        id,
                        updatedProduct.getName(),
                        updatedProduct.getProperties(),
                        product.getCreatedAt(),
                        OffsetDateTime.now(),
                        product.getParent());
                DB.set(indexOfProduct, saved);

                return Optional.of(saved);
            }

            throw new UpdateProductException("Database error. Failed to persist product updates.");
        }).orElseGet(Optional::empty);
    }
}