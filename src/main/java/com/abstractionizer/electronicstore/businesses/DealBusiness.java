package com.abstractionizer.electronicstore.businesses;

import com.abstractionizer.electronicstore.enumerations.DealType;
import jakarta.servlet.http.HttpServletRequest;

public interface DealBusiness {

    void createDeal(DealType type, HttpServletRequest request);
}
