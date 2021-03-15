package com.compensate.api.challenge.dao;

import com.compensate.api.challenge.model.ProductEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao {

  ProductEntity insert(ProductEntity product);

  Page<ProductEntity> selectAll(Pageable pageRequest);

  List<ProductEntity> selectAll();

  Optional<ProductEntity> selectById(UUID id);

  Optional<ProductEntity> updateById(UUID id, ProductEntity product);
}
