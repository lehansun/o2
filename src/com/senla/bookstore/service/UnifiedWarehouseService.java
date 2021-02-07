package com.senla.bookstore.service;

import com.senla.bookstore.api.service.WarehouseService;
import com.senla.bookstore.dao.WarehouseSetDao;
import com.senla.bookstore.exception.NoSuckBookInWarehouseException;
import com.senla.bookstore.model.BookWarehouse;
import com.senla.bookstore.model.Order;
import com.senla.bookstore.model.book.*;

import java.util.*;

public class UnifiedWarehouseService extends AbstractService<BookWarehouse> implements WarehouseService {

    public UnifiedWarehouseService(WarehouseSetDao dao) {
        super(dao);
    }

    @Override
    public BookWarehouse createWarehouse(String address) {
        return new BookWarehouse(address);
    }

    @Override
    public BookWarehouse getWarehouseByAddress(String address) {
        WarehouseSetDao dao = (WarehouseSetDao) this.dao;
        return dao.getByAddress(address);
    }

    @Override
    public boolean addNewBookToWarehouse(BookWarehouse warehouse, Book book) {
        Bookshelf bookshelf;
        boolean addedSuccessfully;
        Map<Bookshelf, Set<Book>> books = warehouse.getBooks();

        if (containsBookshelfFor(warehouse, book)) {
            bookshelf = getSuitableBookshelf(warehouse, book);
        } else {
            bookshelf = new Bookshelf(book);
            books.put(bookshelf, new HashSet<>());
        }

        Set<Book> suitableBookshelf = books.get(bookshelf);
        addedSuccessfully = suitableBookshelf.add(book);
        if (addedSuccessfully) {
            bookshelf.increaseSize();
        }
        return addedSuccessfully;
    }


    @Override
    public Book getBookByDescription(BookWarehouse warehouse, Description description) throws NoSuckBookInWarehouseException {
        if (containsBookshelfFor(warehouse, description) &&
                getSuitableBookshelf(warehouse, description).getSize()>0
        ) {
            Bookshelf bookshelf = getSuitableBookshelf(warehouse, description);
            return (Book) warehouse.getBooks().get(bookshelf).toArray()[0];
        }
        return null;
    }

    @Override
    public Book getBookByDescription(BookWarehouse warehouse, String title, String author, int publicationYear) throws NoSuckBookInWarehouseException {
        return getBookByDescription(warehouse, new Description(title, author, publicationYear));
    }

    @Override
    public List<Book> getBookList(BookWarehouse warehouse) {
        List<Book> books = new ArrayList<>();
        warehouse.getBooks().values().forEach(books::addAll);
        return books;
    }

    @Override
    public List<Book> getBookListByDescription(BookWarehouse warehouse, String author, String title, int publicationYear) throws NoSuckBookInWarehouseException {
        Description description = new Description(author, title, publicationYear);
        return getBookListByDescription(warehouse, description);
    }

    @Override
    public List<Book> getBookListByDescription(BookWarehouse warehouse, Description description) throws NoSuckBookInWarehouseException {
        if (containsBookshelfFor(warehouse, description)) {
            Bookshelf bookshelf = getSuitableBookshelf(warehouse, description);
            Set<Book> bookSet = warehouse.getBooks().get(bookshelf);
            return new ArrayList<>(bookSet);
        }
        throw new NoSuckBookInWarehouseException("Искомая книга не найдена");
    }

    @Override
    public boolean writeOffConcreteBook(BookWarehouse warehouse, Book book) {

        if (containsBookshelfFor(warehouse, book)) {
            Bookshelf bookshelf = getSuitableBookshelf(warehouse, book);
            boolean isWrittenOffSuccessfully = warehouse.getBooks().get(bookshelf).remove(book);
            if (isWrittenOffSuccessfully) {
                bookshelf.reduceSize();
            }
            return isWrittenOffSuccessfully;
        }

        return false;
    }

    @Override
    public boolean writeOffBooksByDescription(BookWarehouse warehouse, Description description) {
        if (containsBookshelfFor(warehouse, description)) {
            Bookshelf bookshelf = getSuitableBookshelf(warehouse, description);
            warehouse.getBooks().get(bookshelf).clear();
            bookshelf.setSize(0);
            return true;
        }
        return false;
    }

    @Override
    public void writeOff(Order order) throws NoSuckBookInWarehouseException {
        BookWarehouse warehouse = getById(order.getWarehouseId());
        List<AbstractBook> resultList = new ArrayList<>();
        List<Book> bookList;
        for (AbstractBook abstractBook : order.getBooks()) {
            Bookshelf bookshelf = (Bookshelf) abstractBook;
            bookList = getBookListByDescription(warehouse, bookshelf.getDescription());
            int count = 0;
            for (Book book : bookList) {
                if (count == bookshelf.getSize()) break;
                if (book.getStatus() == BookStatus.ORDERED) {
                    book.setStatus(BookStatus.SOLD);
                    writeOffConcreteBook(warehouse, book);
                    resultList.add(book);
                    count++;
                }
            }
        }
        order.setBooks(resultList);
    }

    @Override
    public boolean writeOffBooksByDescription(BookWarehouse warehouse, String title, String author, int publicationYear) {
        return writeOffBooksByDescription(warehouse, new Description(title, author, publicationYear));
    }


    @Override
    public boolean containsBookshelfFor(BookWarehouse warehouse, String title, String author, int publicationYear) {
        return containsBookshelfFor(warehouse, new Description(title, author, publicationYear));
    }

    @Override
    public boolean containsBookshelfFor(BookWarehouse warehouse, Description description) {
        return getSuitableBookshelf(warehouse, description) != null;
    }

    @Override
    public boolean containsBookshelfFor(BookWarehouse warehouse, Book book) {
        return getSuitableBookshelf(warehouse, book) != null;
    }


    @Override
    public Bookshelf getSuitableBookshelf(BookWarehouse warehouse, String title, String author, int publicationYear) {
        Description description = new Description(title, author, publicationYear);
        return getSuitableBookshelf(warehouse, description);
    }

    @Override
    public Bookshelf getSuitableBookshelf(BookWarehouse warehouse, Description description) {
        for (Bookshelf bookshelf : warehouse.getBooks().keySet()) {
            if (bookshelf.getDescription().equals(description)) {
                return bookshelf;
            }
        }
        return null;
    }

    @Override
    public Bookshelf getSuitableBookshelf(BookWarehouse warehouse, Book book) {
        for (Bookshelf bookshelf : warehouse.getBooks().keySet()) {
            if (bookshelf.suitable(book)) return bookshelf;
        }
        return null;
    }

    @Override
    public Set<Bookshelf> getAssortment(BookWarehouse warehouse) {
        return warehouse.getBooks().keySet();
    }


}
