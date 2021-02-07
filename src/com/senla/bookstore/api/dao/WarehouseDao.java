package com.senla.bookstore.api.dao;

import com.senla.bookstore.model.BookWarehouse;

public interface WarehouseDao extends GenericDao<BookWarehouse> {

    BookWarehouse getByAddress(String address);
}
