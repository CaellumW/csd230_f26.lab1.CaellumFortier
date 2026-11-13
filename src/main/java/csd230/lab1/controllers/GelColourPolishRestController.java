package csd230.lab1.controllers;

import csd230.lab1.entities.GelColourPolishEntity;
import csd230.lab1.repositories.GelColourPolishEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/gelcolourpolishes")
@Tag(name = "Gel_colour_polishes", description = "Operations for managing colour gel polishes! =)")
public class GelColourPolishRestController {
    @Autowired
    private GelColourPolishEntityRepository colourGelRepository;

    @GetMapping
    @Operation(description = "Retrieves all colour gel polishes")
    @ApiResponse(responseCode = "200", description = "All colour gel polishes found")
    public List<GelColourPolishEntity> getAllColourGelPolishes() {
        return colourGelRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Finds a specific colour gel by its ID")
    @ApiResponse(responseCode = "200", description = "Colour gel found!")
    public ResponseEntity<GelColourPolishEntity> getColourGelById(@PathVariable Long id) {
        return colourGelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ColourGelNotFoundException(id));
    }

    @PostMapping("/add")
    @Operation(description = "Adds a *pre made* colour gel object")
    @ApiResponse(responseCode = "200", description = "Colour gel added")
    public GelColourPolishEntity addColourGel(@RequestBody GelColourPolishEntity colourGel) {
        return colourGelRepository.save(colourGel);
    }

    @PutMapping("/{id}")
    @Operation(description = "Updates an existing colour gel with new information")
    @ApiResponse(responseCode = "200", description = "Colour gel successfully updates")
    public ResponseEntity<GelColourPolishEntity> updateColourGel(
            @PathVariable Long id,
            @RequestBody GelColourPolishEntity updatedColourGel) {

        return colourGelRepository.findById(id)
                .map(colourGel -> {
                    colourGel.setGelType(updatedColourGel.getGelType());
                    colourGel.setPrice(updatedColourGel.getPrice());
                    colourGel.setShadeName(updatedColourGel.getShadeName());
                    colourGel.setStock(updatedColourGel.getStock());
                    colourGel.setGlitter(updatedColourGel.isGlitter());
                    return ResponseEntity.ok(colourGelRepository.save(colourGel));
                })
                .orElseThrow(() -> new ColourGelNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a specific colour gel using its ID")
    @ApiResponse(responseCode = "200", description = "Colour gel deleted.")
    public ResponseEntity<Void> deleteColourGel(@PathVariable Long id) {
        if (!colourGelRepository.existsById(id)) {
            throw new ColourGelNotFoundException(id);
        }

        colourGelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
