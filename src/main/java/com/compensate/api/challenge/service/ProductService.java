package com.compensate.api.challenge.service;

import com.compensate.api.challenge.dao.ProductDao;
import com.compensate.api.challenge.exception.InvalidIdException;
import com.compensate.api.challenge.exception.ProductNotFoundException;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.request.ProductRequest;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ProductService {

    @Autowired
    @Qualifier("fakeDao")
    private ProductDao productDao;

    public Page<ProductEntity> getAll(Pageable pageRequest) {

        return productDao.selectAll(pageRequest);
    }

    public ProductEntity create(final ProductRequest productRequest) {
        final ProductEntity entity = new ProductEntity();
        entity.setName(productRequest.getName());
        entity.setProperties(productRequest.getProperties());

        if (!Strings.isNullOrEmpty(productRequest.getParentId())) {
            entity.setParent(findParent(productRequest.getParentId()));
        }

        return productDao.insert(entity);
    }

    public Optional<ProductEntity> update(final UUID id, final ProductRequest productRequest) {
        final ProductEntity entity = new ProductEntity();
        entity.setName(productRequest.getName());
        entity.setProperties(productRequest.getProperties());

        if (!Strings.isNullOrEmpty(productRequest.getParentId())) {
            entity.setParent(findParent(productRequest.getParentId()));
        }

        return productDao.updateById(id, entity);
    }

    public Optional<ProductEntity> get(final UUID id) {

        return productDao.selectById(id);
    }

    private ProductEntity findParent(final String parentId) {
        try {
            final UUID id = UUID.fromString(parentId);
            final Optional<ProductEntity> parentOpt = productDao.selectById(id);

            if (parentOpt.isPresent()) {
                return parentOpt.get();
            } else {
                throw new ProductNotFoundException(String.format("Parent product with id %s does not exist", id));
            }
        } catch (final IllegalArgumentException ex) {
            throw new InvalidIdException(String.format("Parent id %s is not valid", parentId));
        }
    }
}
