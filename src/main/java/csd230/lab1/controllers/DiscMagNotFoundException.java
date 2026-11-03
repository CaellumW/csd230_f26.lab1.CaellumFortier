package csd230.lab1.controllers;

public class DiscMagNotFoundException extends Exception{

    public DiscMagNotFoundException(Long id) {
        super("DiscMag not found with id: " + id);
    }
}
