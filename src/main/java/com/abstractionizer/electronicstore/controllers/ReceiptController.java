package com.abstractionizer.electronicstore.controllers;

import com.abstractionizer.electronicstore.businesses.ReceiptBusiness;
import com.abstractionizer.electronicstore.model.receipt.ReceiptVo;
import com.abstractionizer.electronicstore.response.SuccessResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {

    private final ReceiptBusiness receiptBusiness;

    public ReceiptController(ReceiptBusiness receiptBusiness) {
        this.receiptBusiness = receiptBusiness;
    }

    @GetMapping("/{basketId}")
    public SuccessResp<ReceiptVo> getReceipt(@PathVariable String basketId){
        return new SuccessResp<>(receiptBusiness.getReceipt(basketId));
    }
}
