package com.abstractionizer.electronicstore.service.impl;

import com.abstractionizer.electronicstore.enumerations.ProductStatus;
import com.abstractionizer.electronicstore.enumerations.ProductType;
import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.model.product.CreateProductDto;
import com.abstractionizer.electronicstore.service.ProductService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;
import com.abstractionizer.electronicstore.storage.rdbms.mappers.ProductMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.abstractionizer.electronicstore.errors.Error.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public void checkIfInputDuplicateNames(int inputSize, int setSize) {
        if(inputSize != setSize){
            throw new BusinessException(BAD_REQUEST_ERROR, "Duplicate product names");
        }
    }

    @Override
    public Set<String> getProductNameSet(@NotNull final Collection<CreateProductDto> dtos) {
        return dtos.stream()
                .map(CreateProductDto::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public int countByProductName(@NotNull final Collection<String> productNames) {
        if(productNames.isEmpty()){
            return 0;
        }
        return productMapper.countByNames(productNames);
    }

    @Override
    public void insertBatch(@NotNull final Collection<ProductEntity> entities) {
        if(entities.isEmpty()){
            return;
        }
        productMapper.insertBatch(entities);
    }

    @Override
    public void ifNameExistsThenThrow(int count) {
        if(count > 0){
            throw new BusinessException(DATA_IS_CREATED, "Product(s) has been created");
        }
    }

    @Override
    public ProductEntity findById(@NotNull final Integer productId) {
        return productMapper.findById(productId);
    }

    @Override
    public void ifProductNotExistsThenThrow(@NotNull final Integer productId) {
        if(Objects.isNull(findById(productId))){
            throw new BusinessException(DATA_NOT_FOUND, String.format("Product id '%s' was not found", productId));
        }
    }

    @Override
    public void updateProduct(ProductEntity entity) {
        if(productMapper.updateProduct(entity) != 1){
            throw new BusinessException(UPDATE_DATA_FAIL, String.format("Update data failed, details: %S", entity));
        }
    }

}
