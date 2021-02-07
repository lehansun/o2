package com.senla.bookstore.model.book;

import com.senla.bookstore.model.Entity;

public abstract class AbstractBook extends Entity {
    private Description description;

    public AbstractBook(String title, String author, int publicationYear) {
        description = new Description(title, author, publicationYear);
    }

    public AbstractBook(Description description) {
        this.description = description;
    }

    public String getTitle() {
        return description.getTitle();
    }

    public String getAuthor() {
        return description.getAuthor();
    }

    public int getPublicationYear() {
        return description.getPublicationYear();
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}
