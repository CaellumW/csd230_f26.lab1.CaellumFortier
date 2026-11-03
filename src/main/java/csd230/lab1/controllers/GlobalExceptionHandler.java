package csd230.lab1.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequestMapping("/api/rest/advice")
@Tag(name = "RC advice", description = "Gracefully handles errors")
public class GlobalExceptionHandler {

//    @ExceptionHandler(MagazineNotFoundException.class)
//    public ResponseEntity<String> handleMagazineNotFound(
//            MagazineNotFoundException ex) {
//        return ResponseEntity.status(404)
//                .body(ex.getMessage());
//    }
//
//    @ExceptionHandler(BookNotFoundException.class)
//    public ResponseEntity<String> handleBookNotFound(
//            BookNotFoundException ex) {
//        return ResponseEntity.status(404)
//                .body(ex.getMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGeneralException(
//            Exception ex) {
//        return ResponseEntity.status(500)
//                .body("Something went wrong");
//    }

//    EXAMPLE CODE ABOVE



}