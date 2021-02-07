package com.senla.bookstore.api.dao;

import com.senla.bookstore.model.Request;
import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.model.book.Description;

public interface RequestDao extends GenericDao<Request> {

    Request getRequestIfStatusNEW(Long warehouseId, Book book);
    Request getRequestIfStatusNEW(Long warehouseId, Description description);
}
