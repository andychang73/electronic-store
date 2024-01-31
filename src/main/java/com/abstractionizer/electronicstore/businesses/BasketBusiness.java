package com.abstractionizer.electronicstore.businesses;

import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;

public interface BasketBusiness {
    String putProductIntoBasket(String basketId, ProductEntity product);
}
