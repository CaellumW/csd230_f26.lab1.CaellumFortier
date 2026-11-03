package csd230.lab1.controllers;

public class BookNotFoundException extends Exception {

    public BookNotFoundException(Long id) {
        super("Book not found with id: " + id);
    }
}
