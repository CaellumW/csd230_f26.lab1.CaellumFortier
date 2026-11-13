package csd230.lab1.controllers;

public class ColourGelNotFoundException extends RuntimeException{

    public ColourGelNotFoundException(Long id) {
        super("Colour gel not found with id: " + id);
    }
}
