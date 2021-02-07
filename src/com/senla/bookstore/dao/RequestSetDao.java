package com.senla.bookstore.dao;

import com.senla.bookstore.api.dao.RequestDao;
import com.senla.bookstore.model.Request;
import com.senla.bookstore.model.Status;
import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.model.book.Description;

public class RequestSetDao extends AbstractSetDao<Request> implements RequestDao {
    private static final RequestSetDao ENTITY = new RequestSetDao();

    private RequestSetDao() {
    }

    public static RequestSetDao getENTITY() {
        return ENTITY;
    }

    @Override
    public Request getRequestIfStatusNEW(Long warehouseId, Book book) {
        for (Request request : getAll()) {
            if (request.getWarehouseId().equals(warehouseId) &&
                    request.getStatus().equals(Status.NEW) &&
                    request.getBookshelf().suitable(book)
            ){
                return request;
            }
        }
        return null;
    }

    @Override
    public Request getRequestIfStatusNEW(Long warehouseId, Description description) {
        for (Request request : getAll()) {
            if (request.getWarehouseId().equals(warehouseId) &&
                    request.getStatus().equals(Status.NEW) &&
                    request.getBookshelf().getDescription().equals(description)
            ){
                return request;
            }
        }
        return null;
    }
}
