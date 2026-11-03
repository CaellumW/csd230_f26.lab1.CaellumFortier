package csd230.lab1.controllers;

public class MagazineNotFoundException extends Exception{

    public MagazineNotFoundException(Long id) {
        super("Magazine not found with id: " + id);
    }
}
