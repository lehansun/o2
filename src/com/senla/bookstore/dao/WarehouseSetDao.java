package com.senla.bookstore.dao;

import com.senla.bookstore.api.dao.WarehouseDao;
import com.senla.bookstore.model.BookWarehouse;

public class WarehouseSetDao extends AbstractSetDao<BookWarehouse> implements WarehouseDao {
    private static final WarehouseSetDao ENTITY = new WarehouseSetDao();

    private WarehouseSetDao() {
    }

    public static WarehouseSetDao getENTITY() {
        return ENTITY;
    }

    @Override
    public BookWarehouse getByAddress(String address) {
        for (BookWarehouse e : super.getAll()) {
            if (e.getAddress().equals(address)) return e;
        }
        return null;
    }

}
