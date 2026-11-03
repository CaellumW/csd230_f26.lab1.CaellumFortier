package csd230.lab1.controllers;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}
