package com.senla.bookstore.api.service;

import com.senla.bookstore.model.BookWarehouse;
import com.senla.bookstore.model.Order;
import com.senla.bookstore.model.Request;
import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.model.book.Bookshelf;

public interface RequestService extends GenericService<Request> {

    Request create(BookWarehouse warehouse, Bookshelf bookshelf);
    void setRequestStatusCOMPLETED(BookWarehouse warehouse, Book book);
    boolean checkUncomplitedRequests(Order order);
}
