package com.abstractionizer.electronicstore.businesses;

import com.abstractionizer.electronicstore.model.basket.BasketVo;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;

public interface BasketBusiness {
    BasketVo putProductIntoBasket(String basketId, ProductEntity product);

    BasketVo removeProductFromBasket(String basketId, Integer productId);
}
