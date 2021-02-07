package com.senla.bookstore.service.facade;

import com.senla.bookstore.api.service.*;
import com.senla.bookstore.exception.NoSuchBooksForOrderException;
import com.senla.bookstore.exception.NoSuckBookInWarehouseException;
import com.senla.bookstore.model.*;
import com.senla.bookstore.model.book.*;

import java.util.List;
import java.util.Set;

public class ElectronicBookStore  {
    private final BookService bookService;
    private final WarehouseService warehouseService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final RequestService requestService;

    public ElectronicBookStore(BookService bookService, WarehouseService warehouseService, OrderService orderService, CustomerService customerService, RequestService requestService) {
        this.bookService = bookService;
        this.warehouseService = warehouseService;
        this.orderService = orderService;
        this.customerService = customerService;
        this.requestService = requestService;
    }

    public void addBooksToWarehouse(BookWarehouse warehouse, Book book) {
        warehouseService.addNewBookToWarehouse(warehouse, book);
        requestService.setRequestStatusCOMPLETED(warehouse, book);
        bookService.setInStoke(book);
        bookService.save(book);
    }

    public void addBooksToWarehouse(BookWarehouse warehouse, List<Book> books) {
        books.forEach(b-> addBooksToWarehouse(warehouse, b));
    }

    public void writeOffBooksByDescription(BookWarehouse warehouse, Description description) throws NoSuckBookInWarehouseException {
        List<Book> books = warehouseService.getBookListByDescription(warehouse, description);
        warehouseService.writeOffBooksByDescription(warehouse, description);
        bookService.reNew(books);
    }

    public void writeOffBooksByDescription(BookWarehouse warehouse, String title, String author, int publicationYear) throws NoSuckBookInWarehouseException {
        writeOffBooksByDescription(warehouse, new Description(title, author, publicationYear));
    }


    public BookWarehouse createWarehouse(String address) {
        return warehouseService.createWarehouse(address);
    }

    public void saveWarehouse(BookWarehouse warehouse) {
        warehouseService.save(warehouse);
    }

    public Book createBook(String title, String author, int publicationYear, int cost) {
        return bookService.create(title, author, publicationYear, cost);
    }

    public void saveBook(Book book) {
        bookService.save(book);
    }

    public Customer createCustomer(String name, String surname, String email) {
        return customerService.create(name, surname, email);
    }

    public void saveCustomer(Customer customer) {
        customerService.save(customer);
    }

    public Set<Bookshelf> getAssortment(BookWarehouse warehouse) {
        return warehouseService.getAssortment(warehouse);
    }

    public Customer getCustomer(String name, String surname, String email) {
        return customerService.getCustomerByDescription(name, surname, email);
    }

    public Order createOrder(Customer customer, BookWarehouse warehouse, String title, String author, int publicationYear, int numberOfCopies) throws NoSuckBookInWarehouseException, NoSuchBooksForOrderException {
//      Проверяем, есть ли книга на складе (в ассортименте)
        if (warehouseService.containsBookshelfFor(warehouse, title, author, publicationYear)) {
//          Книга есть

            Bookshelf bookshelf = warehouseService.getSuitableBookshelf(warehouse, title, author, publicationYear);
//          Проверяем есть ли необходимое количество экземпляров на складе
            if (bookshelf.getSize() >= numberOfCopies) {
//              Книг достаточно, создаем заказ:

                Order order = orderService.create(customer, title, author,  publicationYear, numberOfCopies, warehouse.getId());
                int price = calculateOrderPrice(order, warehouse);
                orderService.setPriceToOrder(order, price);
                orderService.save(order);
                List<Book> books = warehouseService.getBookListByDescription(warehouse, title, author,  publicationYear);
                bookService.setOrdered(books, numberOfCopies);
                return order;
            } else {
//              Книг не достаточно, создаем запрос:
                Request request = requestService.create(warehouse, bookshelf);
                requestService.save(request);
                throw new NoSuchBooksForOrderException("Not enough books in stock (" +bookshelf.getSize() + " pcs), request created");
            }
        } else {
//          Книги нет в ассортименте, бросаем исключение
            throw new NoSuckBookInWarehouseException("The requested book is not for sale");
        }
    }

    public void cancelOrder(Order order) throws NoSuckBookInWarehouseException {
        orderService.cancelOrder(order);
        BookWarehouse warehouse = warehouseService.getById(order.getWarehouseId());
        List<Book> bookList;
        for (AbstractBook abstractBook : order.getBooks()) {
            Bookshelf bookshelf = (Bookshelf) abstractBook;
            bookList = warehouseService.getBookListByDescription(warehouse, bookshelf.getDescription());
            bookService.unOrder(bookList, bookshelf.getSize());
        }
    }

    public void reNew(Order order) throws NoSuckBookInWarehouseException {
        BookWarehouse warehouse = warehouseService.getById(order.getWarehouseId());
        List<Book> bookList;
        for (AbstractBook abstractBook : order.getBooks()) {
            Bookshelf bookshelf = (Bookshelf) abstractBook;
            bookList = warehouseService.getBookListByDescription(warehouse, bookshelf.getDescription());
            bookService.setOrdered(bookList, bookshelf.getSize());
        }
        orderService.reNewOrder(order);
    }

    public void completeOrder(Order order) throws NoSuckBookInWarehouseException {
        if (requestService.checkUncomplitedRequests(order)) {
            warehouseService.writeOff(order);
            orderService.complete(order);
        }
    }

    private int calculateOrderPrice(Order order, BookWarehouse warehouse) throws NoSuckBookInWarehouseException {
        int price = 0;
        List<AbstractBook> books = order.getBooks();
        if (books.get(0) instanceof Bookshelf) {
            for (AbstractBook book : books) {
                Book warehouseBook = warehouseService.getBookByDescription(warehouse, book.getDescription());
                Bookshelf bookshelf = (Bookshelf) book;
                int count =  bookshelf.getSize();
                price += count*warehouseBook.getCost();
            }
        } else {
            for (AbstractBook book : books) {
                Book orderedBook = (Book) book;
                price += orderedBook.getCost();
            }
        }
        return price;
    }

    public List<Book> getNewBooksByDescription(Description description) {
        return bookService.getNewBooksByDescription(description);
    }

    public Request createRequest(BookWarehouse warehouse, Bookshelf absentBook) {
        return requestService.create(warehouse, absentBook);
    }

    public void addNewBookToWarehouse(BookWarehouse warehouse, String title, String author, int publicationYear, int cost) {
        Book book = bookService.create(title, author, publicationYear, cost);
        addBooksToWarehouse(warehouse, book);
    }

    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    public List<BookWarehouse> getAllWarehouses() {
        return warehouseService.getAll();
    }

    public List<Customer> getAllCustomers() {
        return customerService.getAll();
    }

    public BookWarehouse getWarehouseByAddress(String address) {
        return warehouseService.getWarehouseByAddress(address);
    }

    public void writeOffConcreteBook(BookWarehouse warehouse, Book book) {
        warehouseService.writeOffConcreteBook(warehouse, book);
        bookService.reNew(book);
    }

    public Book getBookFromWarehouse(BookWarehouse warehouse, String title, String author, int publicationYear) throws NoSuckBookInWarehouseException {
        return warehouseService.getBookByDescription(warehouse, title, author, publicationYear);
    }

    public List<Book> getAllBooksFromWarehouse(BookWarehouse warehouse) {
        return warehouseService.getBookList(warehouse);
    }

}
