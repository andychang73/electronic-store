package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.DealBusiness;
import com.abstractionizer.electronicstore.enumerations.DealType;
import com.abstractionizer.electronicstore.factories.DealFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class DealBusinessImpl implements DealBusiness {

    private final DealFactory dealFactory;

    public DealBusinessImpl(DealFactory dealFactory) {
        this.dealFactory = dealFactory;
    }

    @Override
    public void createDeal(@NonNull final DealType type, @NonNull final HttpServletRequest request) {
        dealFactory.createDeal(type, request);
    }
}
