package com.senla.bookstore.service;

import com.senla.bookstore.api.dao.RequestDao;
import com.senla.bookstore.api.service.RequestService;
import com.senla.bookstore.dao.RequestSetDao;
import com.senla.bookstore.model.BookWarehouse;
import com.senla.bookstore.model.Order;
import com.senla.bookstore.model.Request;
import com.senla.bookstore.model.Status;
import com.senla.bookstore.model.book.AbstractBook;
import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.model.book.Bookshelf;

public class UnifiedRequestService extends AbstractService<Request> implements RequestService {

    public UnifiedRequestService(RequestSetDao dao) {
        super(dao);
    }


//    @Override
//    public Request create(BookWarehouse warehouse, String title, String author, int publicationYear) {
//        Description description = new Description(title, author, publicationYear);
//        return create(warehouse,description);
//    }

    @Override
    public Request create(BookWarehouse warehouse, Bookshelf bookshelf) {

//      Если на книгу уже был оставлен хотябы один запрос:
        for (Request request : getAll()) {
            if (request.getStatus() == Status.NEW &&
                    request.getBookshelf().equals(bookshelf) &&
                    request.getWarehouseId().equals(warehouse.getId())
            ){
                request.getBookshelf().increaseSize();
                return request;
            }
        }

//      Если на книгу не было оставлено ни одного запроса:
        Bookshelf emptyBookshelf = new Bookshelf(bookshelf.getDescription());
        emptyBookshelf.increaseSize();
        Request newRequest = new Request(emptyBookshelf, warehouse.getId());
        save(newRequest);
        return newRequest;
    }

    @Override
    public void setRequestStatusCOMPLETED(BookWarehouse warehouse, Book book) {
        RequestDao dao = (RequestDao) super.dao;
        if (dao.getRequestIfStatusNEW(warehouse.getId(), book) != null) {
            dao.getRequestIfStatusNEW(warehouse.getId(), book).setStatus(Status.COMPLETED);
        }
    }

    @Override
    public boolean checkUncomplitedRequests(Order order) {
        RequestDao dao = (RequestDao) super.dao;
        for (AbstractBook book : order.getBooks()) {
            if (dao.getRequestIfStatusNEW(order.getWarehouseId(), book.getDescription()) != null) {
                return false;
            }
        }
        return true;
    }
}
