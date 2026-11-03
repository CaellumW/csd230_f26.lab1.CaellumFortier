package csd230.lab1.controllers;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
