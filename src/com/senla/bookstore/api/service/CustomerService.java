package com.senla.bookstore.api.service;

import com.senla.bookstore.model.Customer;

public interface CustomerService extends GenericService<Customer> {

    Customer create(String name, String surname, String email);
    Customer getCustomerByDescription(String name, String surname, String email);

}
