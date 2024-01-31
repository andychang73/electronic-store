package com.abstractionizer.electronicstore.businesses;

import com.abstractionizer.electronicstore.model.product.BasketDto;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;

public interface BasketBusiness {
    BasketDto putProductIntoBasket(String basketId, ProductEntity product);

    BasketDto removeProductFromBasket(String basketId, Integer productId);
}
