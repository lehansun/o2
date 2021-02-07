package com.senla.bookstore.dao;

import com.senla.bookstore.api.dao.GenericDao;
import com.senla.bookstore.model.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractSetDao<T extends Entity> implements GenericDao<T> {
    private final Set<T> repository = new HashSet<>();

    @Override
    public void save(T element) {
        repository.add(element);
    }

    @Override
    public T getById(Long id) {
        for (T element : repository) {
            if (id.equals(element.getId())) return element;
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(repository);
    }

    @Override
    public void delete(T element) {
        repository.remove(element);
    }

    @Override
    public T update(T element) {
        return null;
    }
}
