package com.senla.bookstore.api.service;

import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.model.book.BookStatus;
import com.senla.bookstore.model.book.Description;

import java.util.List;

public interface BookService extends GenericService<Book> {

    Book create(String title, String author, int publicationYear, int cost);

//    List<Book> getBooksByDescription(String title, String author, int publicationYear);
    List<Book> getBooksByDescription(Description description);
    List<Book> getNewBooksByDescription(Description description);

    void changeStatus(Book book, BookStatus inStoke);
    void setInStoke(Book book);
    void setOrdered(Book book);
    void setOrdered(List<Book> books, int numberOfCopies);
    void reNew(Book book);
    void reNew(List<Book> books);

    void unOrder(List<Book> books, int numberOfCopies);
}
