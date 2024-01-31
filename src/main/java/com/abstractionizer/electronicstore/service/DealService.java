package com.abstractionizer.electronicstore.service;

import com.abstractionizer.electronicstore.storage.rdbms.entities.DealEntity;

import java.util.List;

public interface DealService {
    void insert(DealEntity deal);
    
    void ifApplyOrderExistThenThrow(Integer applyOrder);

    void ifDealNameExistThenThrow(String name);

    List<DealEntity> getAllDealsInAscOrderByApplyOrder();
}
