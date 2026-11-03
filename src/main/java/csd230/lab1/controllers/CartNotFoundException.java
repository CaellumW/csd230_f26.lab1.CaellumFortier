package csd230.lab1.controllers;


public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException(Long id) {
        super("Cart not found with id: " + id);
    }

    public CartNotFoundException() {
        super("Cart not found.");
    }
}
