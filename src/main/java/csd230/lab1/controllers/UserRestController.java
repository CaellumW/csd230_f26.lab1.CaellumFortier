package csd230.lab1.controllers;

import csd230.lab1.entities.UserEntity;
import csd230.lab1.repositories.UserEntityRepository;
import org.springframework.security.core.userdetails.User;
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
@RequestMapping("/api/rest/users")
@Tag(name = "Users", description = "Operations for managing users! =)")
public class UserRestController {
    @Autowired
    private UserEntityRepository userRepo;

    @GetMapping("/")
    @Operation(description = "Retrieves all users")
    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Finds a specific user by their ID")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return userRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    @Operation(description = "Adds a new user to the user repository")
    public UserEntity addUser(@RequestBody UserEntity user) {
        return userRepo.save(user);
    }

    @PutMapping("/{id}")
    @Operation(description = "Updates an existing user with new information")
    public ResponseEntity<UserEntity> updateUser(
            @PathVariable Long id,
            @RequestBody UserEntity updatedUser) {

        return userRepo.findById(id)
                .map(user -> {
                    user.setRole(updatedUser.getRole());
                    user.setUsername(updatedUser.getUsername());
                    return ResponseEntity.ok(userRepo.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a specific user by their ID")
    public ResponseEntity<Void> deleteDiscMag(@PathVariable Long id) {
        if (!userRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
