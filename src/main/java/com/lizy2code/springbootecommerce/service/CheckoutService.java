package com.lizy2code.springbootecommerce.service;

import com.lizy2code.springbootecommerce.dto.Purchase;
import com.lizy2code.springbootecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
