package com.senla.bookstore.model;

import com.senla.bookstore.model.book.Bookshelf;


public class Request extends Entity {
    private Bookshelf bookshelf;
    private Long warehouseId;
    private Status status;

    public Request(Bookshelf bookshelf, Long warehouseId) {
        this.bookshelf = bookshelf;
        this.warehouseId = warehouseId;
        this.status = Status.NEW;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{\n\t" +
                bookshelf +
                ", \n\twarehouseId=" + warehouseId +
                ", \n\tstatus=" + status +
                "\n}";
    }
}
