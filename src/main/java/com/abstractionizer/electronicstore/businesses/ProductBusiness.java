package com.abstractionizer.electronicstore.businesses;

import com.abstractionizer.electronicstore.model.product.BasketDto;
import com.abstractionizer.electronicstore.model.product.CreateProductDto;

import java.util.List;

public interface ProductBusiness {

    void createProducts(List<CreateProductDto> dtos);

    void remove(Integer productId);


    BasketDto selectProduct(Integer productId, String basketId);

    BasketDto removeProduct(Integer productId, String basketId);
}
