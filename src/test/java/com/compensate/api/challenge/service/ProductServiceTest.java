package com.compensate.api.challenge.service;

import com.compensate.api.challenge.dao.ProductDao;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.request.ProductRequest;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @MockBean
    private ProductDao productDao;

    @Captor
    ArgumentCaptor<Pageable> pageableArgumentCaptor;

    @Captor
    ArgumentCaptor<ProductEntity> productEntityArgumentCaptor;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productDao);
    }

    @Test
    public void getAllPassCorrectPageable() {
        Pageable expectedPageable = PageRequest.of(2, 4);

        productService.getAll(expectedPageable);
        verify(productDao, times(1)).selectAll(pageableArgumentCaptor.capture());

        Pageable actualPageable = pageableArgumentCaptor.getValue();
        assertThat(actualPageable).isEqualTo(expectedPageable);
    }

    @Test
    public void createSavesProductEntity() {
        final ProductRequest productRequest = new ProductRequest("Test", Maps.newLinkedHashMap());

        productService.create(productRequest);
        verify(productDao, times(1)).insert(productEntityArgumentCaptor.capture());

        final ProductEntity actualProductEntity = productEntityArgumentCaptor.getValue();
        assertThat(actualProductEntity.getName()).isEqualTo("Test");
        assertThat(actualProductEntity.getProperties()).isEqualTo(Maps.newLinkedHashMap());
    }

    @Test
    public void updateSavesUpdatedProductEntity() {
        final ProductRequest productRequest = new ProductRequest("Test upd", Maps.newLinkedHashMap());
        final UUID id = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");

        productService.update(id, productRequest);
        verify(productDao, times(1)).updateById(eq(id), productEntityArgumentCaptor.capture());

        final ProductEntity actualProductEntity = productEntityArgumentCaptor.getValue();
        assertThat(actualProductEntity.getName()).isEqualTo("Test upd");
        assertThat(actualProductEntity.getProperties()).isEqualTo(Maps.newLinkedHashMap());
    }

}