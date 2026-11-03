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
    public List<TicketEntity> getAllTickets() {
        return ticketRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketEntity> getTicketById(@PathVariable Long id) {
        return ticketRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public TicketEntity addTicket(@RequestBody TicketEntity ticket) {
        return ticketRepo.save(ticket);
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        if (!ticketRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        ticketRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
