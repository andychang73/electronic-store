package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.ProductBusiness;
import com.abstractionizer.electronicstore.converters.ProductConverter;
import com.abstractionizer.electronicstore.model.product.CreateProductDto;
import com.abstractionizer.electronicstore.service.ProductService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;
import com.abstractionizer.electronicstore.utils.RedisUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.abstractionizer.electronicstore.constant.RedisKey.getRKeyCreateProduct;

@Service
public class ProductBusinessImpl implements ProductBusiness {

    private final RedisUtil redisUtil;
    private final ProductService productService;

    public ProductBusinessImpl(RedisUtil redisUtil, ProductService productService) {
        this.redisUtil = redisUtil;
        this.productService = productService;
    }

    @Override
    public void createProducts(@NonNull final List<CreateProductDto> dtos) {

        redisUtil.doWithRedisLockOrThrow(getRKeyCreateProduct(), () ->{
            Set<String> productNames = productService.getProductNameSet(dtos);

            productService.checkIfInputDuplicateNames(dtos.size(), productNames.size());

            int count = productService.countByProductName(productNames);

            productService.ifNameExistsThenThrow(count);

            List<ProductEntity> products = ProductConverter.INSTANCE.toProductEntities(dtos);

            productService.insertBatch(products);
        });
    }
}
