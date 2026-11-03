package csd230.lab1.controllers;

public class PublicationNotFoundException extends Exception{

    public PublicationNotFoundException(Long id) {
        super("Publication not found with id: " + id);
    }
}
