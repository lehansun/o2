package com.senla.bookstore.api.dao;

import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.model.book.Description;

import java.util.List;

public interface BookDao extends GenericDao<Book> {

    List<Book> getByDescription(Description description);
    List<Book> getNewByDescription(Description description);
    List<Book> getByTitle(String title);
    List<Book> getByAuthor(String author);

}
