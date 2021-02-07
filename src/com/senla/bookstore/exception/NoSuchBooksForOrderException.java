package com.senla.bookstore.exception;

public class NoSuchBooksForOrderException extends Throwable {
    public NoSuchBooksForOrderException(String message) {
        super(message);
    }
}
