package csd230.lab1.controllers;

import csd230.lab1.entities.PublicationEntity;
import csd230.lab1.repositories.PublicationEntityRepository;
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
@RequestMapping("/api/rest/publications")
@Tag(name = "Publications", description = "Operations for managing publications! =)")
public class PublicationRestController {
    @Autowired
    private PublicationEntityRepository publicationRepo;

    @GetMapping("/")
    @Operation(description = "Retrieves all publications")
    @ApiResponse(responseCode = "200", description = "All publications found")
    public List<PublicationEntity> getAllPublicataions() {
        return publicationRepo.findAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Retrieves a specific publication by its ID")
    @ApiResponse(responseCode = "200", description = "Publication found")
    public ResponseEntity<PublicationEntity> getPublicationById(@PathVariable Long id) {
        return publicationRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PublicationNotFoundException(id));
    }

    @PostMapping("/add")
    @Operation(description = "Adds a new publication to the publication repository")
    @ApiResponse(responseCode = "200", description = "Publication added to the repository")
    public PublicationEntity addPublication(@RequestBody PublicationEntity publication) {
        return publicationRepo.save(publication);
    }

    @PutMapping("/{id}")
    @Operation(description = "Updates an existing publication with new information")
    @ApiResponse(responseCode = "200", description = "Publication updated")
    public ResponseEntity<PublicationEntity> updatePublication(
            @PathVariable Long id,
            @RequestBody PublicationEntity updatedPublication) {

        return publicationRepo.findById(id)
                .map(publication -> {
                    publication.setCopies(updatedPublication.getCopies());
                    publication.setTitle(updatedPublication.getTitle());
                    return ResponseEntity.ok(publicationRepo.save(publication));
                })
                .orElseThrow(() -> new PublicationNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a publication via its ID")
    @ApiResponse(responseCode = "200", description = "Publication deleted")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        if (!publicationRepo.existsById(id)) {
            throw new PublicationNotFoundException(id);
        }

        publicationRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
