package com.senla.bookstore.service;

import com.senla.bookstore.api.service.OrderService;
import com.senla.bookstore.dao.OrderSetDao;
import com.senla.bookstore.model.Customer;
import com.senla.bookstore.model.Order;
import com.senla.bookstore.model.Status;
import com.senla.bookstore.model.book.*;

import java.util.ArrayList;
import java.util.List;

public class UnifiedOrderService extends AbstractService<Order> implements OrderService {

    public UnifiedOrderService(OrderSetDao dao) {
        super(dao);
    }

    @Override
    public Order create(Customer customer, String title, String author, int publicationYear, int numberOfCopies, Long warehouseId) {
        Description description = new Description(title, author, publicationYear);
        return create(customer, description, numberOfCopies, warehouseId);
    }

    @Override
    public Order create(Customer customer, Description description, int numberOfCopies, Long warehouseId) {
        Bookshelf bookshelf = new Bookshelf(description);
        bookshelf.setSize(numberOfCopies);
        List<AbstractBook> books = new ArrayList<>();
        books.add(bookshelf);
        return new Order(customer, books, warehouseId);
    }

    @Override
    public void setPriceToOrder(Order order, int price) {
        order.setPrice(price);
    }

    @Override
    public void cancelOrder(Order order) {
        if (order.getStatus() == Status.NEW) {
            order.setStatus(Status.CANCELED);
        }
    }

    @Override
    public void reNewOrder(Order order) {
        if (order.getStatus() == Status.CANCELED) {
            order.setStatus(Status.NEW);
        }
    }

    @Override
    public void complete(Order order) {
        if (order.getStatus() == Status.NEW) {
            order.setStatus(Status.COMPLETED);
        }
    }

}
