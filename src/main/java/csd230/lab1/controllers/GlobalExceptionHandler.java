package csd230.lab1.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequestMapping("/api/rest/advice")
@Tag(name = "RC advice", description = "Gracefully handles errors")
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFound(
            BookNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<String> handleCartNotFound(
            CartNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DiscMagNotFoundException.class)
    public ResponseEntity<String> handleDiscMagNotFound(
            DiscMagNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MagazineNotFoundException.class)
    public ResponseEntity<String> handleMagazineNotFound(
            MagazineNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFound(
            OrderNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(
            ProductNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(PublicationNotFoundException.class)
    public ResponseEntity<String> handlePublicationNotFound(
            PublicationNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<String> handleTicketNotFound(
            TicketNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(
            UserNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(
            Exception ex) {
        return ResponseEntity.status(500)
                .body(ex.getMessage());
    }
}