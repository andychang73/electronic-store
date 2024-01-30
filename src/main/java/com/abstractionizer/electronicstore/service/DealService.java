package com.abstractionizer.electronicstore.service;

import com.abstractionizer.electronicstore.storage.rdbms.entities.DealEntity;

public interface DealService {
    void insert(DealEntity deal);
    
    void ifApplyOrderExistThenThrow(Integer applyOrder);

    void ifDealNameExistThenThrow(String name);
}
