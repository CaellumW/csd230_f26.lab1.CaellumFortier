package csd230.lab1.controllers;

public class CartNotFoundException extends Exception{

    public CartNotFoundException(Long id) {
        super("Cart not found with id: " + id);
    }
}
