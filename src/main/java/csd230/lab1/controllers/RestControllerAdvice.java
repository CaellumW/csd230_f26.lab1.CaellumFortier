package csd230.lab1.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest/advice")
@Tag(name = "RC advice", description = "Gracefully handles errors")
public class RestControllerAdvice {

}