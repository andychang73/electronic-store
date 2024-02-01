package com.abstractionizer.electronicstore.businesses;

import com.abstractionizer.electronicstore.enumerations.DealType;
import com.abstractionizer.electronicstore.model.receipt.ReceiptDto;
import jakarta.servlet.http.HttpServletRequest;

public interface DealBusiness {

    void createDeal(DealType type, HttpServletRequest request);

    ReceiptDto applyDeals(ReceiptDto receiptDto);
}
