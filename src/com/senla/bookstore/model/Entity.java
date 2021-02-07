package com.senla.bookstore.model;

public abstract class Entity {
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
