package csd230.lab1.controllers;

import csd230.lab1.entities.TicketEntity;
import csd230.lab1.repositories.TicketEntityRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/rest/tickets")
@Tag(name = "Tickets", description = "Operations for managing tickets! =)")
public class TicketRestController {
    @Autowired
    private TicketEntityRepository ticketRepo;

    @GetMapping("/")
    @Operation(description = "Retrieves all tickets")
    @ApiResponse(responseCode = "200", description = "All tickets found")
    public List<TicketEntity> getAllTickets() {
        return ticketRepo.findAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Retrieves a specific ticket by its ID")
    @ApiResponse(responseCode = "200", description = "Ticket found")
    public ResponseEntity<TicketEntity> getTicketById(@PathVariable Long id) {
        return ticketRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    @Operation(description = "Adds a new ticket to the ticket repository")
    @ApiResponse(responseCode = "200", description = "Ticket added to the repository")
    public TicketEntity addTicket(@RequestBody TicketEntity ticket) {
        return ticketRepo.save(ticket);
    }

    @PutMapping("/{id}")
    @Operation(description = "Updates an existing ticket with new information")
    @ApiResponse(responseCode = "200", description = "Ticket updated")
    public ResponseEntity<TicketEntity> updateTicket(
            @PathVariable Long id,
            @RequestBody TicketEntity updatedTicket) {

        return ticketRepo.findById(id)
                .map(ticket -> {
                    ticket.setDescription(updatedTicket.getDescription());
                    ticket.setPrice(updatedTicket.getPrice());
                    return ResponseEntity.ok(ticketRepo.save(ticket));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a specific ticket by its ID")
    @ApiResponse(responseCode = "200", description = "Ticket deleted")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        if (!ticketRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        ticketRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
