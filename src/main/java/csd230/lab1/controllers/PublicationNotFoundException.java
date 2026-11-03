package csd230.lab1.controllers;

public class PublicationNotFoundException extends RuntimeException{

    public PublicationNotFoundException(Long id) {
        super("Publication not found with id: " + id);
    }
}
