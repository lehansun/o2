package com.senla.bookstore.model;

import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.model.book.Bookshelf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BookWarehouse extends Entity {
    private final String address;
    private final Map<Bookshelf, Set<Book>> books;

    public BookWarehouse(String address) {
        this.address = address;
        this.books = new HashMap<>();
    }


    public String getAddress() {
        return address;
    }


    public Map<Bookshelf, Set<Book>> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "BookWarehouse{" +
                "address='" + address + '\'' +
                ", books=" + books.keySet() +
                '}';
    }
}
