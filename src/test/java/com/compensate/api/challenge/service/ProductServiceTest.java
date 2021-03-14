package com.compensate.api.challenge.service;

import com.compensate.api.challenge.dao.ProductDao;
import com.compensate.api.challenge.exception.InvalidIdException;
import com.compensate.api.challenge.exception.ProductNotFoundException;
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

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
        final ProductRequest productRequest = new ProductRequest("Test", Maps.newLinkedHashMap(), null);

        productService.create(productRequest);
        verify(productDao, times(1)).insert(productEntityArgumentCaptor.capture());

        final ProductEntity actualProductEntity = productEntityArgumentCaptor.getValue();
        assertThat(actualProductEntity.getName()).isEqualTo("Test");
        assertThat(actualProductEntity.getProperties()).isEqualTo(Maps.newLinkedHashMap());
        assertThat(actualProductEntity.getParent()).isNull();
    }

    @Test
    public void updateSavesUpdatedProductEntity() {
        final ProductRequest productRequest = new ProductRequest("Test upd", Maps.newLinkedHashMap(), null);
        final UUID id = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");

        productService.update(id, productRequest);
        verify(productDao, times(1)).updateById(eq(id), productEntityArgumentCaptor.capture());

        final ProductEntity actualProductEntity = productEntityArgumentCaptor.getValue();
        assertThat(actualProductEntity.getName()).isEqualTo("Test upd");
        assertThat(actualProductEntity.getProperties()).isEqualTo(Maps.newLinkedHashMap());
        assertThat(actualProductEntity.getParent()).isNull();
    }

    @Test
    public void updateSavesProductWithParent() {
        final String createdAt = "2020-04-29T12:50:08+00:00";
        final String modifiedAt = "2020-05-29T12:50:08+00:00";
        final String parentId = "3b30c4aa-0d5a-47de-aaac-1eb4c49f626c";
        final UUID childProductId = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");

        final ProductEntity expectedParentProduct = new ProductEntity(
                UUID.fromString(parentId),
                "Parent product",
                Maps.newLinkedHashMap(),
                OffsetDateTime.parse(createdAt),
                OffsetDateTime.parse(modifiedAt),
                null);

        final ProductRequest productRequest = new ProductRequest("Test upd", Maps.newLinkedHashMap(), parentId);
        when(productDao.selectById(UUID.fromString(parentId))).thenReturn(Optional.of(expectedParentProduct));

        productService.update(childProductId, productRequest);
        verify(productDao, times(1)).updateById(eq(childProductId), productEntityArgumentCaptor.capture());

        final ProductEntity actualParentProduct = productEntityArgumentCaptor.getValue().getParent();
        assertThat(actualParentProduct).isEqualTo(expectedParentProduct);
    }

    @Test
    public void updateShouldThrowProductNotFoundExceptionIfParentDoesNotExist() {
        final String parentId = "3b30c4aa-0d5a-47de-aaac-1eb4c49f626c";
        final UUID childProductId = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");

        final ProductRequest productRequest = new ProductRequest("Test upd", Maps.newLinkedHashMap(), parentId);
        when(productDao.selectById(UUID.fromString(parentId))).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.update(childProductId, productRequest);
        });
    }

    @Test
    public void updateShouldThrowInvalidIdExceptionIfParentIdIsInvalid() {
        final String parentId = "invalid-id";
        final UUID childProductId = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");

        final ProductRequest productRequest = new ProductRequest("Test upd", Maps.newLinkedHashMap(), parentId);

        assertThrows(InvalidIdException.class, () -> {
            productService.update(childProductId, productRequest);
        });
    }

}