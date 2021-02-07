package com.senla.bookstore.api.service;

import com.senla.bookstore.model.Entity;

import java.util.List;

public interface GenericService<T extends Entity> {

    void save(T element);
    T getById(Long id);
    List<T> getAll();
    void delete(T element);
}
