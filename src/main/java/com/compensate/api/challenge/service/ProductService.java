package com.compensate.api.challenge.service;

import com.compensate.api.challenge.dao.ProductDao;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.request.ProductRequest;
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

        return productDao.insert(entity);
    }

    public Optional<ProductEntity> update(final UUID id, final ProductRequest productRequest) {
        final ProductEntity entity = new ProductEntity();
        entity.setName(productRequest.getName());
        entity.setProperties(productRequest.getProperties());

        return productDao.updateById(id, entity);
    }

    public Optional<ProductEntity> get(final UUID id) {

        return productDao.selectById(id);
    }
}
