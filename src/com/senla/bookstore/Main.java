package com.senla.bookstore;

import com.senla.bookstore.dao.*;
import com.senla.bookstore.exception.NoSuchBooksForOrderException;
import com.senla.bookstore.exception.NoSuckBookInWarehouseException;
import com.senla.bookstore.model.BookWarehouse;
import com.senla.bookstore.model.Customer;
import com.senla.bookstore.model.Order;
import com.senla.bookstore.model.Request;
import com.senla.bookstore.model.book.Book;
import com.senla.bookstore.model.book.Bookshelf;
import com.senla.bookstore.model.book.Description;
import com.senla.bookstore.service.*;
import com.senla.bookstore.service.facade.ElectronicBookStore;

import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchBooksForOrderException, NoSuckBookInWarehouseException {
        new Starter().init();

        ElectronicBookStore bookStore = new ElectronicBookStore(
                new UnifiedBookService(BookSetDao.getEntity()),
                new UnifiedWarehouseService(WarehouseSetDao.getENTITY()),
                new UnifiedOrderService(OrderSetDao.getENTITY()),
                new UnifiedCustomerService(CustomerSetDao.getEntity()),
                new UnifiedRequestService(RequestSetDao.getENTITY())
        );

        /**
         * ▪1. Списать книгу со склада (перевести в статус “отсутствует”)
         */

        System.out.println("\n▪1. Списать книгу со склада (перевести в статус “отсутствует”)");

        System.out.println("\n\tAssortment before writingOff: \n");

        BookWarehouse warehouse = bookStore.getWarehouseByAddress("Брест");
        bookStore.getAssortment(warehouse).forEach(System.out::println);

        bookStore.writeOffBooksByDescription(warehouse, "Источник", "Эйн Ренд", 1943);
        System.out.println("\n\tAssortment after writingOff: \n");
        bookStore.getAssortment(warehouse).forEach(System.out::println);

        /**
         * ▪2. Создать заказ
         */

        System.out.println("\n▪2. Создать заказ");

        Customer customerToOrder = bookStore.getCustomer("Name1", "Surname1", "email1@mail.ru");
        Order order = bookStore.createOrder(customerToOrder, warehouse, "Совершенный код", "Стив МакКоннелл", 1997, 2);
        System.out.println("\n\tOrder created: \n");
        System.out.println(order);

        /**
         * ▪3. Отменить заказ
         */

        System.out.println("\n▪3. Отменить заказ");


        bookStore.cancelOrder(order);
        System.out.println("\n\tOrder canceled: \n");
        System.out.println(order);

        /**
         * ▪4. Изменить статус заказа (новый)
         */

        System.out.println("\n▪4. Изменить статус заказа (новый)");

        bookStore.reNew(order);
        System.out.println("\n\tOrder status changed to NEW: \n");
        System.out.println(order);


        /**
         * ▪5. Изменить статус заказа (выполнен)
         *     Заказ не может быть завершен, до тех пор, пока запрос книги не будет выполнен
         */

        System.out.println("\n▪5. Изменить статус заказа (выполнен)");

        bookStore.completeOrder(order);
        System.out.println("\n\tOrder status changed to COMPLETED: \n");
        System.out.println(order + "\n");

        /**
         * ▪6. Оставить запрос на книгу
         */

        System.out.println("\n▪6. Оставить запрос на книгу");


        bookStore.getAssortment(warehouse).forEach(System.out::println);
        Bookshelf absentBook = null;
//      Look for absent book in the warehouse
        for (Bookshelf bookshelf : bookStore.getAssortment(warehouse)) {
            if (bookshelf.getSize() == 0) absentBook = bookshelf;
        }

//      Создаем 10 запросов на книгу, которой нет на складе
        Request request = null;
        for (int i = 0; i < 10; i++) {
            request = bookStore.createRequest(warehouse, absentBook);
        }
        System.out.println("\n" + request);

        /**
        * ▪7. Добавить книгу на склад (закрывает все запросы книги и меняет ее статус на “в наличии”)
        */

        System.out.println("\n▪7. Добавить книгу на склад (закрывает все запросы книги и меняет ее статус на “в наличии”)");

        Description description = new Description("Источник", "Эйн Ренд", 1943);
        List<Book> books = bookStore.getNewBooksByDescription(description);
        bookStore.addBooksToWarehouse(warehouse, books);

        System.out.println();
        bookStore.getAssortment(warehouse).forEach(System.out::println);

        System.out.println("\n" + request);
    }

}
