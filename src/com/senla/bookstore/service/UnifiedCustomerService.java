package com.senla.bookstore.service;

import com.senla.bookstore.api.service.CustomerService;
import com.senla.bookstore.dao.CustomerSetDao;
import com.senla.bookstore.model.Customer;

public class UnifiedCustomerService extends AbstractService<Customer> implements CustomerService {

    public UnifiedCustomerService(CustomerSetDao dao) {
        super(dao);
    }

    @Override
    public Customer create(String name, String surname, String email) {
        return new Customer(name, surname, email);
    }

    @Override
    public Customer getCustomerByDescription(String name, String surname, String email) {
        for (Customer customer : getAll()) {
            if (name.equalsIgnoreCase(customer.getName()) &&
                    surname.equalsIgnoreCase(customer.getSurname()) &&
                    email.equalsIgnoreCase(customer.getEmail())
            ) return customer;
        }
        return null;
    }
}
