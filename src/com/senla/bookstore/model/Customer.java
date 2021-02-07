package com.senla.bookstore.model;

public class Customer extends Entity {
    private String name;
    private String surname;
    private String email;

    public Customer(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                surname + ' ' + name +
                ", email: " + email +
                '}';
    }
}
