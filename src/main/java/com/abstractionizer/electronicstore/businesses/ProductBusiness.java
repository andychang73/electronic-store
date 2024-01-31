package com.abstractionizer.electronicstore.businesses;

import com.abstractionizer.electronicstore.model.product.CreateProductDto;

import java.util.List;

public interface ProductBusiness {

    void createProducts(List<CreateProductDto> dtos);

    void remove(Integer productId);


    String selectProduct(Integer productId, String basketId);
}
