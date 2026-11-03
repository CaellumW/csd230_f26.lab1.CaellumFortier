package csd230.lab1.controllers;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Long id) {
        super("Order not found with id: " + id);
    }
}
