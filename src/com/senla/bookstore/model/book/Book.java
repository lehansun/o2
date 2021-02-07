package com.senla.bookstore.model.book;


public class Book extends AbstractBook {
    private int cost;
    private BookStatus status;

    public Book(String title, String author, int publicationYear, int cost) {
        super(title, author, publicationYear);
        this.cost = cost;
        this.status = BookStatus.NEW;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                getTitle() + "\", year " +
                getAuthor() + ", \"" +
                getPublicationYear() +
                ", cost=" + cost +
                ", status=" + status +
                '}';
    }
}
