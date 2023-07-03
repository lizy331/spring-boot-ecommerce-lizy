package com.lizy2code.springbootecommerce.service;

import com.lizy2code.springbootecommerce.dao.CustomerRepository;
import com.lizy2code.springbootecommerce.dto.Purchase;
import com.lizy2code.springbootecommerce.dto.PurchaseResponse;
import com.lizy2code.springbootecommerce.entity.Customer;
import com.lizy2code.springbootecommerce.entity.Order;
import com.lizy2code.springbootecommerce.entity.OrderItem;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrieve the info from DTO
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItemSet();
        orderItems.forEach(item -> order.add(item));

        // populate order with billing and shipping address
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();
        customer.add(order);

        // save to database
        customerRepository.save(customer);

        // return the response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateTrackingNumber() {

        // generate a random uuid (universally unique) number uuid version-4
        // because we want a number that is hard to guess, random, and unique
        return UUID.randomUUID().toString();
    }
}
