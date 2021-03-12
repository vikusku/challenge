package com.compensate.api.challenge.dao;

import com.compensate.api.challenge.exception.UpdateProductException;
import com.compensate.api.challenge.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakeProductDataAccessService implements ProductDao {

    private static List<ProductEntity> DB = new ArrayList<>();

    @Override
    public ProductEntity insert(ProductEntity product) {
        final ProductEntity saved = new ProductEntity(
                UUID.randomUUID(),
                product.getName(),
                product.getProperties(),
                OffsetDateTime.now(),
                OffsetDateTime.now());

        DB.add(saved);
        return saved;
    }

    @Override
    public Page<ProductEntity> selectAll(Pageable pageable) {
        if (DB.isEmpty()) {
            return Page.empty();
        }

        final int start = Math.min((int)pageable.getOffset(), DB.size());
        final int end = Math.min((start + pageable.getPageSize()), DB.size());

        return new PageImpl<>(DB.subList(start, end), pageable, DB.size());
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
                        OffsetDateTime.now());
                DB.set(indexOfProduct, saved);

                return Optional.of(saved);
            }

            throw new UpdateProductException("Database error. Failed to persist product updates.");
        }).orElseGet(Optional::empty);
    }
}