package com.abstractionizer.electronicstore.service;

import com.abstractionizer.electronicstore.model.product.ProductVo;

import java.math.BigDecimal;
import java.util.Collection;

public interface ReceiptService {

    BigDecimal getTotalPrice(Collection<ProductVo> products);
}
