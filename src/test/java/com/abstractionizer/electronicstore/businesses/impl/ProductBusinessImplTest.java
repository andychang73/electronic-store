package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.BasketBusiness;
import com.abstractionizer.electronicstore.businesses.ProductBusiness;
import com.abstractionizer.electronicstore.converters.ProductConverter;
import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.model.basket.BasketVo;
import com.abstractionizer.electronicstore.model.product.CreateProductDto;
import com.abstractionizer.electronicstore.service.ProductService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;
import com.abstractionizer.electronicstore.utils.RedisUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductBusinessImplTest {

    @Mock
    RedisUtil redisUtil;

    @Mock
    BasketBusiness basketBusiness;

    @Mock
    ProductService productService;

    @Mock
    ProductBusiness productBusiness;

    @BeforeEach
    public void init() {
        productBusiness = new ProductBusinessImpl(redisUtil, basketBusiness, productService);
    }

    @Test
    public void testCreateProduct_WhenCouldNotAcquireLock_ThenThrowBusinessException() {

        doThrow(BusinessException.class)
                .when(redisUtil)
                .doWithRedisLockOrThrow(anyString(), any(RedisUtil.DoInRedisLock.class));

        assertThrows(BusinessException.class,
                () -> productBusiness.createProducts(new ArrayList<>()),
                "System is busy please try again later");

        verify(productService, never()).getProductNameSet(any(Collection.class));
        verify(productService, never()).checkIfInputNamesDuplicated(anyInt(), anyInt());
        verify(productService, never()).ifNameExistsThenThrow(any(Collection.class));
        verify(productService, never()).insertBatch(any(Collection.class));
    }

    @Test
    public void testCreateProduct_WhenLockIsAcquired_ThenAllMethodsAreExecuted() {

        List<CreateProductDto> dtos = Arrays.asList(
                new CreateProductDto(),
                new CreateProductDto(),
                new CreateProductDto() // Duplicated product name
        );
        Set<String> productNames = Set.of("Product1", "Product2");


        when(productService.getProductNameSet(dtos)).thenReturn(productNames);
        doAnswer(invocation -> {

            Set<String> names = productService.getProductNameSet(dtos);
            productService.checkIfInputNamesDuplicated(dtos.size(), names.size());
            productService.ifNameExistsThenThrow(names);
            List<ProductEntity> products = ProductConverter.INSTANCE.toProductEntities(dtos);
            productService.insertBatch(products);
            return null;
        }).when(redisUtil).doWithRedisLockOrThrow(anyString(), any());

        // Act and Assert
        productBusiness.createProducts(dtos);

        // Verify interactions
        verify(redisUtil, times(1)).doWithRedisLockOrThrow(anyString(), any());
        verify(productService, times(1)).getProductNameSet(dtos);
        verify(productService, times(1)).checkIfInputNamesDuplicated(dtos.size(), productNames.size());
        verify(productService, times(1)).ifNameExistsThenThrow(productNames);
        verify(productService, times(1)).insertBatch(anyList());
    }

    @Test
    public void testRemove_WhenProductDoesNotExists_ThenThrowBusinessException(){

        doThrow(BusinessException.class)
                .when(productService)
                .ifProductNotExistsThenThrow(anyInt());

        assertThrows(BusinessException.class,
                () -> productBusiness.remove(1),
                "Product id '1' was not found");

        verify(productService, never()).updateProduct(any(ProductEntity.class));
    }

    @Test
    public void testRemove_WhenProductExists_ThenAllMethodsAreExecuted(){

        productBusiness.remove(1);

        verify(productService, times(1)).ifProductNotExistsThenThrow(anyInt());
        verify(productService, times(1)).updateProduct(any(ProductEntity.class));
    }

    @Test
    public void testSelectProduct_WhenProductDoesNotExists_ThenThrowBusinessException(){
        doThrow(BusinessException.class)
                .when(productService)
                .ifProductNotExistsThenThrow(anyInt());

        assertThrows(BusinessException.class,
                () -> productBusiness.selectProduct(1,"1"),
                "Product id '1' was not found");

        verify(productService, never()).ifProductStockInsufficientThenThrow(anyInt());
        verify(productService, never()).reduceProductStockByOne(anyInt());
        verify(basketBusiness, never()).putProductIntoBasket(anyString(), any(ProductEntity.class));
    }

    @Test
    public void testSelectProduct_WhenProductStockInsufficient_ThenThrowBusinessException(){

        ProductEntity product = new ProductEntity();
        product.setId(1);
        product.setStock(0);

        when(productService.selectProductForUpdateOrThrow(anyInt())).thenReturn(product);
        doThrow(BusinessException.class)
                .when(productService)
                .ifProductStockInsufficientThenThrow(anyInt());

        assertThrows(
                BusinessException.class,
                () -> productBusiness.selectProduct(1,"1"),
                "Insufficient stock"
        );

        verify(productService, times(1)).ifProductNotExistsThenThrow(anyInt());
        verify(productService, times(1)).selectProductForUpdateOrThrow(anyInt());
        verify(productService, times(1)).ifProductStockInsufficientThenThrow(anyInt());
        verify(productService, never()).reduceProductStockByOne(anyInt());
        verify(basketBusiness, never()).putProductIntoBasket(anyString(), any(ProductEntity.class));
    }

    @Test
    public void testSelectProduct_WhenAllValidationWentThrow_ThenAllMethodsAreExecuted(){

        ProductEntity product = new ProductEntity();
        product.setId(1);
        product.setStock(0);

        when(productService.selectProductForUpdateOrThrow(anyInt())).thenReturn(product);


        productBusiness.selectProduct(1,"1");

        verify(productService, times(1)).ifProductNotExistsThenThrow(anyInt());
        verify(productService, times(1)).selectProductForUpdateOrThrow(anyInt());
        verify(productService, times(1)).ifProductStockInsufficientThenThrow(anyInt());
        verify(productService, times(1)).reduceProductStockByOne(anyInt());
        verify(basketBusiness, times(1)).putProductIntoBasket(anyString(), any(ProductEntity.class));
    }

    @Test
    public void testRemoveProduct_WhenProductDoesNotExist_ThenThrowBusinessException(){

        doThrow(BusinessException.class)
                .when(productService)
                .ifProductNotExistsThenThrow(anyInt());

        assertThrows(BusinessException.class,
                () -> productBusiness.removeProduct(1,"1"),
                "Product id '1' was not found");

        verify(productService, times(1)).ifProductNotExistsThenThrow(anyInt());
        verify(basketBusiness, never()).removeProductFromBasket(anyString(), anyInt());
        verify(productService, never()).selectProductForUpdateOrThrow(anyInt());
        verify(productService, never()).updateProduct(any(ProductEntity.class));
    }

    @Test
    public void testRemoveProduct_WhenAllValidationWentThrow_ThenAllMethodsAreExecuted(){

        when(basketBusiness.removeProductFromBasket(anyString(),anyInt())).thenReturn(new BasketVo());

        ProductEntity product = new ProductEntity();
        product.setStock(0);
        when(productService.selectProductForUpdateOrThrow(anyInt())).thenReturn(product);

        BasketVo result = productBusiness.removeProduct(1, "1");

        assertNotNull(result);
        verify(productService, times(1)).ifProductNotExistsThenThrow(anyInt());
        verify(basketBusiness, times(1)).removeProductFromBasket(anyString(), anyInt());
        verify(productService, times(1)).selectProductForUpdateOrThrow(anyInt());
        verify(productService, times(1)).updateProduct(any(ProductEntity.class));
    }

}