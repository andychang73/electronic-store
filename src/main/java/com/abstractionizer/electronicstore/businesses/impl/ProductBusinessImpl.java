package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.BasketBusiness;
import com.abstractionizer.electronicstore.businesses.ProductBusiness;
import com.abstractionizer.electronicstore.converters.ProductConverter;
import com.abstractionizer.electronicstore.enumerations.ProductStatus;
import com.abstractionizer.electronicstore.model.basket.BasketVo;
import com.abstractionizer.electronicstore.model.product.CreateProductDto;
import com.abstractionizer.electronicstore.service.ProductService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;
import com.abstractionizer.electronicstore.utils.RedisUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.abstractionizer.electronicstore.constant.RedisKey.getRKeyCreateProduct;

@Slf4j
@Service
public class ProductBusinessImpl implements ProductBusiness {

    private final RedisUtil redisUtil;
    private final BasketBusiness basketBusiness;
    private final ProductService productService;

    public ProductBusinessImpl(RedisUtil redisUtil, BasketBusiness basketBusiness,
                               ProductService productService) {
        this.redisUtil = redisUtil;
        this.basketBusiness = basketBusiness;
        this.productService = productService;
    }

    @Transactional
    @Override
    public void createProducts(@NonNull final List<CreateProductDto> dtos) {

        redisUtil.doWithRedisLockOrThrow(getRKeyCreateProduct(), () ->{

            Set<String> productNames = productService.getProductNameSet(dtos);

            productService.checkIfInputNamesDuplicated(dtos.size(), productNames.size());

            productService.ifNameExistsThenThrow(productNames);

            List<ProductEntity> products = ProductConverter.INSTANCE.toProductEntities(dtos);

            productService.insertBatch(products);
        });
    }

    @Transactional
    @Override
    public void remove(@NonNull final Integer productId) {

        productService.ifProductNotExistsThenThrow(productId);

        ProductEntity product = ProductEntity.builder()
                .id(productId)
                .status(ProductStatus.UNAVAILABLE)
                .build();

        productService.updateProduct(product);
    }

    @Transactional
    @Override
    public BasketVo selectProduct(@NonNull final Integer productId, @Nullable final String basketId) {

        productService.ifProductNotExistsThenThrow(productId);

        ProductEntity product = productService.selectProductForUpdateOrThrow(productId);

        productService.ifProductStockInsufficientThenThrow(product.getStock());

        productService.reduceProductStockByOne(product.getId());

        return basketBusiness.putProductIntoBasket(basketId, product);
    }

    @Transactional
    @Override
    public BasketVo removeProduct(@NonNull final Integer productId, @Nullable final String basketId) {

        productService.ifProductNotExistsThenThrow(productId);

        BasketVo basketVo = basketBusiness.removeProductFromBasket(basketId, productId);

        ProductEntity product = productService.selectProductForUpdateOrThrow(productId);

        product.setStock(product.getStock() + 1);

        productService.updateProduct(product);

        return basketVo;
    }
}
