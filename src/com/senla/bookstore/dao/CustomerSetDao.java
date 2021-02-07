package com.senla.bookstore.dao;

import com.senla.bookstore.api.dao.CustomerDao;
import com.senla.bookstore.model.Customer;

public class CustomerSetDao extends AbstractSetDao<Customer> implements CustomerDao {
    private static final CustomerSetDao ENTITY = new CustomerSetDao();

    private CustomerSetDao() {
    }

    public static CustomerSetDao getEntity() {
        return ENTITY;
    }
}
