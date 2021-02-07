package com.senla.bookstore.dao;

import com.senla.bookstore.api.dao.OrderDao;
import com.senla.bookstore.model.Order;


public class OrderSetDao extends AbstractSetDao<Order> implements OrderDao {
    private static OrderSetDao ENTITY;

    private OrderSetDao() {
    }

    public static synchronized OrderSetDao getENTITY() {
        if(ENTITY == null) ENTITY = new OrderSetDao();
        return ENTITY;
    }

}
