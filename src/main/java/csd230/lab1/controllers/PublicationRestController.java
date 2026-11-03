package csd230.lab1.controllers;

import csd230.lab1.entities.PublicationEntity;
import csd230.lab1.repositories.PublicationEntityRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rest/publications")
public class PublicationRestController {
    @Autowired
    private PublicationEntityRepository publicationRepo;

    @GetMapping("/")
    public List<PublicationEntity> getAllPublicataions() {
        return publicationRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationEntity> getPublicationById(@PathVariable Long id) {
        return publicationRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public PublicationEntity addPublication(@RequestBody PublicationEntity publication) {
        return publicationRepo.save(publication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationEntity> updatePublication(
            @PathVariable Long id,
            @RequestBody PublicationEntity updatedPublication) {

        return publicationRepo.findById(id)
                .map(publication -> {
                    publication.setCopies(updatedPublication.getCopies());
                    publication.setTitle(updatedPublication.getTitle());
                    return ResponseEntity.ok(publicationRepo.save(publication));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        if (!publicationRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        publicationRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
