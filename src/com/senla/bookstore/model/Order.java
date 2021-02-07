package com.senla.bookstore.model;

import com.senla.bookstore.model.book.AbstractBook;
import java.util.List;

public class Order extends Entity {
    private Customer customer;
    private List<AbstractBook> books;
    private Status status;
    private int price;
    private final Long warehouseId;

    public Order(Customer customer, List<AbstractBook> books, Long warehouseId) {
        this.customer = customer;
        this.books = books;
        this.status = Status.NEW;
        this.warehouseId = warehouseId;
    }


    public Customer getCustomer() {
        return customer;
    }

    public List<AbstractBook> getBooks() {
        return books;
    }

    public Status getStatus() {
        return status;
    }

    public int getPrice() {
        return price;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public void setStatus(Status status) {
        this.status = status;
    }

    public void setBooks(List<AbstractBook> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        StringBuilder books = new StringBuilder();
        this.books.forEach(b-> books.append("\n\t\t").append(b));
        return "Order{\n\t" +
                customer +
                ", \n\tbooks = [" + books.toString() +
                "\n\t], \n\tstatus = " + status +
                ", \n\tprice = " + price +
                "\n}";
    }
}
