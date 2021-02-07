package com.senla.bookstore.api.service;

import com.senla.bookstore.exception.NoSuckBookInWarehouseException;
import com.senla.bookstore.model.BookWarehouse;
import com.senla.bookstore.model.Order;
import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.model.book.Bookshelf;
import com.senla.bookstore.model.book.Description;

import java.util.List;
import java.util.Set;

public interface WarehouseService extends GenericService<BookWarehouse>{

    BookWarehouse createWarehouse(String address);
    BookWarehouse getWarehouseByAddress(String address);

    boolean addNewBookToWarehouse(BookWarehouse warehouse, Book book);
    Book getBookByDescription(BookWarehouse warehouse, Description description) throws NoSuckBookInWarehouseException;
    Book getBookByDescription(BookWarehouse warehouse, String title, String author, int publicationYear) throws NoSuckBookInWarehouseException;

    List<Book> getBookList(BookWarehouse warehouse);
    List<Book> getBookListByDescription(BookWarehouse warehouse, String author, String title, int publicationYear) throws NoSuckBookInWarehouseException;
    List<Book> getBookListByDescription(BookWarehouse warehouse, Description description) throws NoSuckBookInWarehouseException;

    boolean writeOffConcreteBook(BookWarehouse warehouse, Book book);
    boolean writeOffBooksByDescription(BookWarehouse warehouse, Description description);

    void writeOff(Order order) throws NoSuckBookInWarehouseException;
    boolean writeOffBooksByDescription(BookWarehouse warehouse, String title, String author, int publicationYear);
    boolean containsBookshelfFor(BookWarehouse warehouse, String title, String author, int publicationYear);

    boolean containsBookshelfFor(BookWarehouse warehouse, Description description);
    boolean containsBookshelfFor(BookWarehouse warehouse, Book book);
    Bookshelf getSuitableBookshelf(BookWarehouse warehouse, String title, String author, int publicationYear);

    Bookshelf getSuitableBookshelf(BookWarehouse warehouse, Description description);

    Bookshelf getSuitableBookshelf(BookWarehouse warehouse, Book book);

    Set<Bookshelf> getAssortment(BookWarehouse warehouse);
}
