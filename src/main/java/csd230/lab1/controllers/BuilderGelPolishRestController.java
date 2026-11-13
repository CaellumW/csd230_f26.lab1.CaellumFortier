package csd230.lab1.controllers;

import csd230.lab1.entities.BuilderGelPolishEntity;
import csd230.lab1.repositories.BuilderGelPolishEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/buildergelpolishes")
@Tag(name = "Builder_gel_polishes", description = "Operations for managing builder gel polishes! =)")
public class BuilderGelPolishRestController {
    @Autowired
    private BuilderGelPolishEntityRepository builderGelRepository;

    @GetMapping
    @Operation(description = "Retrieves all builder gel polishes")
    @ApiResponse(responseCode = "200", description = "All builder gel polishes found")
    public List<BuilderGelPolishEntity> getAllBuilderGelPolishes() {
        return builderGelRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Finds a specific builder gel by its ID")
    @ApiResponse(responseCode = "200", description = "Builder gel found!")
    public ResponseEntity<BuilderGelPolishEntity> getBuilderGelById(@PathVariable Long id) {
        return builderGelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BuilderGelNotFoundException(id));
    }

    @PostMapping("/add")
    @Operation(description = "Adds a *pre made* builder gel object")
    @ApiResponse(responseCode = "200", description = "Builder gel added")
    public BuilderGelPolishEntity addBuilderGel(@RequestBody BuilderGelPolishEntity builderGel) {
        return builderGelRepository.save(builderGel);
    }

    @PutMapping("/{id}")
    @Operation(description = "Updates an existing builder gel with new information")
    @ApiResponse(responseCode = "200", description = "Builder gel successfully updates")
    public ResponseEntity<BuilderGelPolishEntity> updateBuilderGel(
            @PathVariable Long id,
            @RequestBody BuilderGelPolishEntity updatedBuilderGel) {

        return builderGelRepository.findById(id)
                .map(builderGelPolish -> {
                    builderGelPolish.setGelType(updatedBuilderGel.getGelType());
                    builderGelPolish.setPrice(updatedBuilderGel.getPrice());
                    builderGelPolish.setShadeName(updatedBuilderGel.getShadeName());
                    builderGelPolish.setStock(updatedBuilderGel.getStock());
                    return ResponseEntity.ok(builderGelRepository.save(builderGelPolish));
                })
                .orElseThrow(() -> new BuilderGelNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a specific builder gel using its ID")
    @ApiResponse(responseCode = "200", description = "Builder gel deleted.")
    public ResponseEntity<Void> deleteBuilderGel(@PathVariable Long id) {
        if (!builderGelRepository.existsById(id)) {
            throw new BuilderGelNotFoundException(id);
        }

        builderGelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
