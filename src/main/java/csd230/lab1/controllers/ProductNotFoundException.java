package csd230.lab1.controllers;

import csd230.lab1.pojos.Product;

public class ProductNotFoundException extends Exception{

    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}
