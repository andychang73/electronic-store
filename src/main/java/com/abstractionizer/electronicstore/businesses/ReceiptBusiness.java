package com.abstractionizer.electronicstore.businesses;

import com.abstractionizer.electronicstore.model.receipt.ReceiptVo;

public interface ReceiptBusiness {

    ReceiptVo getReceipt(String basketId);
}
