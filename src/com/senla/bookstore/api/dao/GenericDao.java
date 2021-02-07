package com.senla.bookstore.api.dao;

import com.senla.bookstore.model.Entity;

import java.util.List;

public interface GenericDao<T extends Entity> {

    void save(T element);
    T getById(Long id);
    List<T> getAll();
    void delete(T element);
    T update(T element);
}
