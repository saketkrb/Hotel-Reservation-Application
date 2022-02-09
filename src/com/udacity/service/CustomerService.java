package com.udacity.service;

import com.udacity.model.Customer;


import java.util.*;

public class CustomerService {

    private static CustomerService customerServiceInstance = null;

    private CustomerService() {}

    public static CustomerService getInstance() {
        if (customerServiceInstance == null)
            customerServiceInstance = new CustomerService();
        return customerServiceInstance;
    }


    private static final Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

}
