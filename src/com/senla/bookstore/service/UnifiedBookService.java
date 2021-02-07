package com.senla.bookstore.service;

import com.senla.bookstore.api.service.BookService;
import com.senla.bookstore.dao.BookSetDao;
import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.model.book.BookStatus;
import com.senla.bookstore.model.book.Description;

import java.util.List;

public class UnifiedBookService extends AbstractService<Book> implements BookService {

    public UnifiedBookService(BookSetDao dao) {
        super(dao);
    }

    @Override
    public Book create(String title, String author, int publicationYear, int cost) {
        return new Book(title, author, publicationYear, cost); // ToDo: Throw new exception
    }

//    @Override
//    public List<Book> getBooksByDescription(String title, String author, int publicationYear) {
//        Description description = new Description(title, author, publicationYear);
//        BookSetDao dao = (BookSetDao) this.dao;
//        return dao.getByDescription(description);
//    }

    @Override
    public List<Book> getBooksByDescription(Description description) {
        BookSetDao dao = (BookSetDao) this.dao;
        return dao.getByDescription(description);
    }

    @Override
    public List<Book> getNewBooksByDescription(Description description) {
        BookSetDao dao = (BookSetDao) this.dao;
        return dao.getNewByDescription(description);
    }

    @Override
    public void changeStatus(Book book, BookStatus status) {
        book.setStatus(status);
    }

    @Override
    public void reNew(Book book) {
        if (book.getStatus()==BookStatus.IN_STOKE) {
            book.setStatus(BookStatus.NEW);
        };
    }

    @Override
    public void reNew(List<Book> books) {
        books.forEach(this::reNew);
    }

    @Override
    public void unOrder(List<Book> books, int numberOfCopies) {
        int count = 0;
        for (Book book : books) {
            if (count == numberOfCopies) break;
            if (book.getStatus() == BookStatus.ORDERED) {
                setInStoke(book);
                count++;
            }
        }
    }

    @Override
    public void setInStoke(Book book) {
        if (book.getStatus() != BookStatus.SOLD
        ) {
            book.setStatus(BookStatus.IN_STOKE);
        }
    }

    @Override
    public void setOrdered(Book book) {
        if (book.getStatus() == BookStatus.IN_STOKE) {
            book.setStatus(BookStatus.ORDERED);
        }
    }

    @Override
    public void setOrdered(List<Book> books, int numberOfCopies) {
        int count = 0;
        for (Book book : books) {
            if (count == numberOfCopies) break;
            if (book.getStatus() == BookStatus.IN_STOKE) {
                setOrdered(book);
                count++;
            }
        }
    }


}
