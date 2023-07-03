package com.lizy2code.springbootecommerce.dto;

import lombok.Data;

@Data
public class PurchaseResponse {

    // lombok will generate constructor method only for final field
    private final String orderTrackingNumber;

}
