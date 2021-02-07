package com.senla.bookstore;

import com.senla.bookstore.dao.*;
import com.senla.bookstore.model.BookWarehouse;
import com.senla.bookstore.model.Customer;
import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.service.*;
import com.senla.bookstore.service.facade.ElectronicBookStore;

public class Starter {
    public void init() {
        ElectronicBookStore facade = new ElectronicBookStore(
                new UnifiedBookService(BookSetDao.getEntity()),
                new UnifiedWarehouseService(WarehouseSetDao.getENTITY()),
                new UnifiedOrderService(OrderSetDao.getENTITY()),
                new UnifiedCustomerService(CustomerSetDao.getEntity()),
                new UnifiedRequestService(RequestSetDao.getENTITY())
        );

        Book book;

        BookWarehouse warehouse = facade.createWarehouse("Брест");
        warehouse.setId(1L);
        facade.saveWarehouse(warehouse);

        for (int i = 0; i < 10; i++) {

            book = facade.createBook("Атлант расправил плечи", "Эйн Ренд", 1957, 53);
            if ((i < 3)) {
                facade.addBooksToWarehouse(warehouse, book);
            } else {
                facade.saveBook(book);
            }
            book = facade.createBook("Источник", "Эйн Ренд", 1943, 37);
            if ((i < 3)) {
                facade.addBooksToWarehouse(warehouse, book);
            } else {
                facade.saveBook(book);
            }
            book = facade.createBook("Одиночество в сети", "Януш Вишневский", 2001, 24);
            if ((i < 3)) {
                facade.addBooksToWarehouse(warehouse, book);
            } else {
                facade.saveBook(book);
            }
            book = facade.createBook("Совершенный код", "Стив МакКоннелл", 1997, 69);
            if ((i < 3)) {
                facade.addBooksToWarehouse(warehouse, book);
            } else {
                facade.saveBook(book);
            }
        }

        Customer customer;
        for (int i = 1; i < 11; i++) {
            customer = facade.createCustomer("Name"+i, "Surname"+i, "email"+i+"@mail.ru");
            facade.saveCustomer(customer);
        }
    }
}
