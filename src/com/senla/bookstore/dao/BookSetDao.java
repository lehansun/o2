package com.senla.bookstore.dao;

import com.senla.bookstore.api.dao.BookDao;
import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.model.book.BookStatus;
import com.senla.bookstore.model.book.Description;

import java.util.*;

public class BookSetDao extends AbstractSetDao<Book> implements BookDao {

    private static BookSetDao ENTITY;

    private BookSetDao() {
    }

    public static synchronized BookSetDao getEntity() {
        if (ENTITY==null) ENTITY = new BookSetDao();
        return ENTITY;
    }

    @Override
    public List<Book> getByTitle(String title) {
        List<Book> books = new ArrayList<>();
        for (Book book : getAll()) {
            if (book.getTitle().equals(title)) {
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public List<Book> getByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        for (Book book : getAll()) {
            if (book.getAuthor().equals(author)) {
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public List<Book> getByDescription(Description description) {
        List<Book> books = new ArrayList<>();
        for (Book book : getAll()) {
            if (book.getDescription().equals(description)) {
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public List<Book> getNewByDescription(Description description) {
        List<Book> books = new ArrayList<>();
        for (Book book : getByDescription(description)) {
            if (book.getStatus().equals(BookStatus.NEW)) {
                books.add(book);
            }
        }
        return books;
    }

}
