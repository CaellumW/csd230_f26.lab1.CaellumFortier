package csd230.lab1.controllers;

import csd230.lab1.entities.GelPolishEntity;

public class GelPolishNotFoundException extends RuntimeException{
    public GelPolishNotFoundException(Long id) {
        super("Gel polish not found with id: " + id);
    }
}
