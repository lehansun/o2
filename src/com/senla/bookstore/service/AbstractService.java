package com.senla.bookstore.service;

import com.senla.bookstore.api.service.GenericService;
import com.senla.bookstore.dao.AbstractSetDao;
import com.senla.bookstore.model.Entity;

import java.util.List;

public abstract class AbstractService<T extends Entity> implements GenericService<T> {
    protected AbstractSetDao<T> dao;

    public AbstractService(AbstractSetDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public void save(T element) {
        dao.save(element);
    }

    @Override
    public T getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<T> getAll() {
        return dao.getAll();
    }

    @Override
    public void delete(T element) {
        dao.delete(element);
    }
}
