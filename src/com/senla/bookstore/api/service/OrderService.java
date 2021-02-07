package com.senla.bookstore.api.service;

import com.senla.bookstore.model.Customer;
import com.senla.bookstore.model.Order;
import com.senla.bookstore.model.book.Description;

public interface OrderService extends GenericService<Order> {

    Order create(Customer customer, String author, String title, int publicationYear, int numberOfCopies, Long warehouseId);
    Order create(Customer customer, Description description, int numberOfCopies, Long warehouseId);

    void setPriceToOrder(Order order, int price);

    void cancelOrder(Order order);
    void reNewOrder(Order order);
    void complete(Order order);


}
