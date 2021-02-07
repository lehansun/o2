package com.senla.bookstore.model.book;

import java.util.Objects;

public class Bookshelf extends AbstractBook {
    private int count;

    public Bookshelf(Book book) {
        super(book.getTitle(), book.getAuthor(), book.getPublicationYear());
        count = 0;
    }

    public Bookshelf(Description description) {
        super(description);
        count = 0;
    }

    public Bookshelf(String title, String author, int publicationYear) {
        super(title, author, publicationYear);
        count = 0;
    }

    public boolean suitable(Book book) {
        return book.getAuthor().equals(getAuthor())
                && book.getPublicationYear() == getPublicationYear()
                && book.getTitle().equals(getTitle()
        );
    }

    public int getSize() {
        return count;
    }

    public void setSize(int size) {
        this.count = size;
    }

    public void increaseSize() {
        count++;
    }

    public void reduceSize() {
        count--;
    }

    @Override
    public String toString() {
        return "Bookshelf{" +
                getTitle() + "\", year " +
                getAuthor() + ", \"" +
                getPublicationYear() +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bookshelf)) return false;
        Bookshelf bookshelf = (Bookshelf) o;
        return getDescription().equals(bookshelf.getDescription());
    }

}
