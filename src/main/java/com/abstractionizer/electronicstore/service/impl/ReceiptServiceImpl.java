package com.abstractionizer.electronicstore.service.impl;

import com.abstractionizer.electronicstore.model.product.ProductVo;
import com.abstractionizer.electronicstore.service.ReceiptService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    @Override
    public BigDecimal getTotalPrice(@NonNull final Collection<ProductVo> products) {
        return products.stream()
                .map(ProductVo::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
