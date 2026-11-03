package csd230.lab1.controllers;

public class TicketNotFoundException extends Exception{

    public TicketNotFoundException(Long id) {
        super("Ticket not found with id: " + id);
    }
}
