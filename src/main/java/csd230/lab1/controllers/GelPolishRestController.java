package csd230.lab1.controllers;

import csd230.lab1.entities.GelPolishEntity;
import csd230.lab1.repositories.GelPolishEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/gelpolishes")
@Tag(name = "Gel_Polishes", description = "Operations for managing gel polishes! =)")
public class GelPolishRestController {
    @Autowired
    private GelPolishEntityRepository gelPolishRepository;

    @GetMapping("/")
    @Operation(description = "Retrieves all gel polishes")
    @ApiResponse(responseCode = "200", description = "All gel polishes found")
    public List<GelPolishEntity> getAllGelPolishes() {
        return gelPolishRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Retrieves a specific gel polish by its ID")
    @ApiResponse(responseCode = "200", description = "Gel polish found")
    public ResponseEntity<GelPolishEntity> getPolishByID(@PathVariable Long id) {
        return gelPolishRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new GelPolishNotFoundException(id));
    }

    @PostMapping("/add")
    @Operation(description = "Adds a new gel polish to the gel polish repository")
    @ApiResponse(responseCode = "200", description = "Gel polish added to the repository")
    public GelPolishEntity addPolish(@RequestBody GelPolishEntity polish) {
        return gelPolishRepository.save(polish);
    }

    @PutMapping("/{id}")
    @Operation(description = "Updates an existing gel polish with new information")
    @ApiResponse(responseCode = "200", description = "Polish updated")
    public ResponseEntity<GelPolishEntity> updatePolish(
            @PathVariable Long id,
            @RequestBody GelPolishEntity updatedPolish) {

        return gelPolishRepository.findById(id)
                .map(polish -> {
                    polish.setGelType(updatedPolish.getGelType());
                    polish.setShadeName(updatedPolish.getShadeName());
                    polish.setStock(updatedPolish.getStock());
                    return ResponseEntity.ok(gelPolishRepository.save(polish));
                })
                .orElseThrow(() -> new GelPolishNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a gel polish via its ID")
    @ApiResponse(responseCode = "200", description = "Gel polish deleted")
    public ResponseEntity<Void> deletePolish(@PathVariable Long id) {
        if (!gelPolishRepository.existsById(id)) {
            throw new GelPolishNotFoundException(id);
        }

        gelPolishRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
