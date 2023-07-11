package com.lizy2code.springbootecommerce.dto;

import com.lizy2code.springbootecommerce.entity.Address;
import com.lizy2code.springbootecommerce.entity.Customer;
import com.lizy2code.springbootecommerce.entity.Order;
import com.lizy2code.springbootecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

    @Override
    public String toString() {
        return "Purchase{" +
                "customer=" + customer +
                ", shippingAddress=" + shippingAddress +
                ", billingAddress=" + billingAddress +
                ", order=" + order +
                ", orderItemSet=" + orderItems +
                '}';
    }
}
