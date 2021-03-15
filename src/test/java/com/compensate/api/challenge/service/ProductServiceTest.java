package com.compensate.api.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.compensate.api.challenge.dao.ProductDao;
import com.compensate.api.challenge.exception.InvalidIdException;
import com.compensate.api.challenge.exception.ProductNotFoundException;
import com.compensate.api.challenge.model.ProductEntity;
import com.compensate.api.challenge.request.ProductRequest;
import com.compensate.api.challenge.util.PaginatedResponseUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

  @MockBean
  private ProductDao productDao;

  @MockBean
  private PaginatedResponseUtil paginatedResponseUtil;

  @Captor
  ArgumentCaptor<Pageable> pageableArgumentCaptor;

  @Captor
  ArgumentCaptor<ProductEntity> productEntityArgumentCaptor;

  private ProductService productService;

  @BeforeEach
  void setUp() {
    productService = new ProductService(productDao, paginatedResponseUtil);
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
  public void getShouldThrowInvalidIdExceptionIfIdIsInvalid() {
    assertThrows(InvalidIdException.class, () -> {
      productService.get("invalid-id");
    });
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
    final ProductRequest productRequest = new ProductRequest("Product upd", Maps.newLinkedHashMap(),
        null);
    final String createdAt = "2020-04-29T12:50:08+00:00";
    final String modifiedAt = "2020-05-29T12:50:08+00:00";
    final UUID id = UUID.fromString("d56b4377-e906-4c63-955c-70dbb1d919b2");

    final ProductEntity currentProductEntity = new ProductEntity(
        id,
        "Product",
        Maps.newLinkedHashMap(),
        OffsetDateTime.parse(createdAt),
        OffsetDateTime.parse(modifiedAt),
        null
    );

    when(productDao.selectById(id)).thenReturn(Optional.of(currentProductEntity));

    productService.update(id.toString(), productRequest);

    verify(productDao, times(1))
        .updateById(eq(id), productEntityArgumentCaptor.capture());

    final ProductEntity actualProductEntity = productEntityArgumentCaptor.getValue();
    assertThat(actualProductEntity.getName()).isEqualTo("Product upd");
    assertThat(actualProductEntity.getProperties()).isEqualTo(Maps.newLinkedHashMap());
    assertThat(actualProductEntity.getParent()).isNull();
  }

  @Test
  public void updateSavesProductWithParent() {
    final String createdAt = "2020-04-29T12:50:08+00:00";
    final String modifiedAt = "2020-05-29T12:50:08+00:00";
    final String parentId = "3b30c4aa-0d5a-47de-aaac-1eb4c49f626c";
    final String childProductId = "d56b4377-e906-4c63-955c-70dbb1d919b2";

    final ProductEntity childProduct = mock(ProductEntity.class);
    final ProductEntity expectedParentProduct = new ProductEntity(
        UUID.fromString(parentId),
        "Parent product",
        Maps.newLinkedHashMap(),
        OffsetDateTime.parse(createdAt),
        OffsetDateTime.parse(modifiedAt),
        null);

    when(productDao.selectById(UUID.fromString(parentId))).thenReturn(Optional.of(expectedParentProduct));
    when(productDao.selectById(UUID.fromString(childProductId))).thenReturn(Optional.of(childProduct));

    productService.update(childProductId, new ProductRequest("Test upd", Maps.newLinkedHashMap(),
        parentId));

    verify(productDao, times(1))
        .updateById(eq(UUID.fromString(childProductId)), productEntityArgumentCaptor.capture());

    assertThat(productEntityArgumentCaptor.getValue().getParent()).isEqualTo(expectedParentProduct);
  }

  @Test
  public void updateReturnsEmptyOptionalIfProductDoesNotExist() {
    final String productId = "3b30c4aa-0d5a-47de-aaac-1eb4c49f626c";

    when(productDao.selectById(UUID.fromString(productId))).thenReturn(Optional.empty());

    final Optional<ProductEntity> actual =
        productService.update(productId, new ProductRequest("Test upd", Maps.newLinkedHashMap(),null));

    assertThat(actual).isEmpty();
  }

  @Test
  public void updateShouldThrowProductNotFoundExceptionIfParentDoesNotExist() {
    final String parentId = "3b30c4aa-0d5a-47de-aaac-1eb4c49f626c";
    final String childProductId = "d56b4377-e906-4c63-955c-70dbb1d919b2";

    final ProductEntity childProduct = mock(ProductEntity.class);
    when(productDao.selectById(UUID.fromString(childProductId))).thenReturn(Optional.of(childProduct));
    when(productDao.selectById(UUID.fromString(parentId))).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> {
      productService.update(childProductId, new ProductRequest("Test upd", Maps.newLinkedHashMap(),
          parentId));
    });
  }

  @Test
  public void updateShouldThrowInvalidIdExceptionIfParentIdIsInvalid() {
    final String parentId = "invalid-id";
    final String childProductId = "d56b4377-e906-4c63-955c-70dbb1d919b2";

    final ProductEntity childProduct = mock(ProductEntity.class);
    when(productDao.selectById(UUID.fromString(childProductId))).thenReturn(Optional.of(childProduct));

    assertThrows(InvalidIdException.class, () -> {
      productService.update(childProductId, new ProductRequest("Test upd", Maps.newLinkedHashMap(),
          parentId));
    });
  }

  @Test
  public void getAllSubProductsPassCorrectPageable() {
    Pageable expectedPageable = PageRequest.of(0, 5);
    final String createdAt = "2020-04-29T12:50:08+00:00";
    final String modifiedAt = "2020-05-29T12:50:08+00:00";
    final String parentProductId = "d56b4377-e906-4c63-955c-70dbb1d919b2";
    final ProductEntity entity = new ProductEntity(
        UUID.fromString(parentProductId),
        "Parent",
        Maps.newLinkedHashMap(),
        OffsetDateTime.parse(createdAt),
        OffsetDateTime.parse(modifiedAt),
        null
    );

    when(productDao.selectById(UUID.fromString(parentProductId))).thenReturn(Optional.of(entity));
    when(productDao.selectAll()).thenReturn(Lists.newArrayList(entity));

    productService.getAllSubProducts(parentProductId, expectedPageable);
    verify(paginatedResponseUtil, times(1))
        .getPage(eq(Lists.newArrayList()), pageableArgumentCaptor.capture());

    Pageable actualPageable = pageableArgumentCaptor.getValue();
    assertThat(actualPageable).isEqualTo(expectedPageable);
  }

  @Test
  public void getAllSubProductsThrowsProductNotFoundExceptionIfParentProductDoesNotExist() {
    final String parentProductId = "d56b4377-e906-4c63-955c-70dbb1d919b2";

    when(productDao.selectById(UUID.fromString(parentProductId))).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> {
      productService.getAllSubProducts(parentProductId, PageRequest.of(2, 4));
    });
  }

  @Test
  public void getAllSubProductsThrowsInvalidIdExceptionIfParentProductIdIsInvalid() {
    assertThrows(InvalidIdException.class, () -> {
      productService.getAllSubProducts("invalid-parent-product-Id", PageRequest.of(2, 4));
    });
  }

  @Test
  public void getRootShouldReturnRootForMultiLevelTree() {
    final ProductEntity expectedRoot = new ProductEntity();

    final ProductEntity childFirstLevel = new ProductEntity();
    childFirstLevel.setParent(expectedRoot);

    final ProductEntity childSecondLevel = new ProductEntity();
    childSecondLevel.setParent(childFirstLevel);

    final ProductEntity childThirdLevel = new ProductEntity();
    childThirdLevel.setParent(childSecondLevel);

    final ProductEntity childFourthLevel = new ProductEntity();
    childFourthLevel.setParent(childThirdLevel);

    final ProductEntity actualRoot = productService.getRoot(childFourthLevel);
    assertThat(actualRoot).isEqualTo(expectedRoot);
  }

  @Test
  public void getRootShoutReturnItselfForOneLevelTree() {
    final ProductEntity expectedRoot = new ProductEntity();
    expectedRoot.setName("root");

    final ProductEntity actualRoot = productService.getRoot(expectedRoot);
    assertThat(actualRoot).isEqualTo(expectedRoot);
  }

  @Test
  public void getChildrenShouldReturnOnlyDirectChildren() {
    final String rootId = "1626ee1a-3938-4188-ac7d-560ae5f7ac8b";
    final String firstChildId = "14dceb2c-11c8-49f0-a050-307535320d12";
    final String secondChildId = "4406f239-e2de-4957-9381-c412e1e96d49";
    final String grandChildId = "065fd227-fc90-48b5-98bd-1cb14a9de9c5";
    final String randomItemId1 = "78324641-3576-44b8-8a89-4cd77ed65bee";
    final String randomItemId2 = "08bd67cd-6b4e-4e6a-98ed-ac790b72e77f";

    final ProductEntity root = new ProductEntity();
    root.setId(UUID.fromString(rootId));

    final ProductEntity firstChild = new ProductEntity();
    firstChild.setId(UUID.fromString(firstChildId));
    firstChild.setParent(root);

    final ProductEntity secondChild = new ProductEntity();
    secondChild.setId(UUID.fromString(secondChildId));
    secondChild.setParent(root);

    final ProductEntity grandChild = new ProductEntity();
    grandChild.setId(UUID.fromString(grandChildId));
    grandChild.setParent(secondChild);

    final ProductEntity randomItem1 = new ProductEntity();
    randomItem1.setId(UUID.fromString(randomItemId1));

    final ProductEntity randomItem2 = new ProductEntity();
    randomItem2.setId(UUID.fromString(randomItemId2));

    when(productDao.selectAll()).thenReturn(Lists.newArrayList(
        root, firstChild, secondChild, grandChild, randomItem1, randomItem1
    ));

    final List<ProductEntity> children = productService.getChildren(root);
    assertThat(children).containsExactlyInAnyOrder(firstChild, secondChild);
  }

  @Test
  public void getChildrenShouldReturnEmptyCollectionIfNodeDoesNotHaveChildren() {
    final String item1Id = "1626ee1a-3938-4188-ac7d-560ae5f7ac8b";
    final String item2Id = "14dceb2c-11c8-49f0-a050-307535320d12";
    final String item3Id = "4406f239-e2de-4957-9381-c412e1e96d49";

    final ProductEntity item1 = new ProductEntity();
    item1.setId(UUID.fromString(item1Id));

    final ProductEntity item2 = new ProductEntity();
    item2.setId(UUID.fromString(item2Id));

    final ProductEntity item3 = new ProductEntity();
    item3.setId(UUID.fromString(item3Id));

    when(productDao.selectAll()).thenReturn(Lists.newArrayList(item1, item2, item3));
    final List<ProductEntity> children = productService.getChildren(item1);
    assertThat(children).isEmpty();
  }

}