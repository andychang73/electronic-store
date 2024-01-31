package com.abstractionizer.electronicstore.service;

import com.abstractionizer.electronicstore.model.product.CreateProductDto;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;

import java.util.Collection;
import java.util.Set;

public interface ProductService {

    void checkIfInputNamesDuplicated(int inputSize, int setSize);
    Set<String> getProductNameSet(Collection<CreateProductDto> dtos);

    int countByProductName(Collection<String> productNames);

    void insertBatch(Collection<ProductEntity> entities);

    void ifNameExistsThenThrow(Collection<String> productNames);

    ProductEntity findById(Integer productId);
    void ifProductNotExistsThenThrow(Integer productId);

    void updateProduct(ProductEntity entity);

    ProductEntity selectProductForUpdateOrThrow(Integer productId);

    void ifProductStockInsufficientThenThrow(Integer stock);

    void reduceProductStockByOne(Integer id);

}
