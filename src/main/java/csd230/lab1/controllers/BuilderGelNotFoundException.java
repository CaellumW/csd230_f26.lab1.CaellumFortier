package csd230.lab1.controllers;

public class BuilderGelNotFoundException extends RuntimeException{

    public BuilderGelNotFoundException(Long id) {
        super("Builder gel not found with id: " + id);
    }
}
