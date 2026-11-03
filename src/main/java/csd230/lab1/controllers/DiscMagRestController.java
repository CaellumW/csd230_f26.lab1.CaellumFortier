package csd230.lab1.controllers;
import csd230.lab1.entities.DiscMagEntity;
import csd230.lab1.repositories.DiscMagEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/rest/discmag")
@Tag(name = "DiscMags", description = "Operations for managing discmags! =)")
public class DiscMagRestController {
    @Autowired
    private DiscMagEntityRepository discMagRepository;

    @GetMapping("/")
    public List<DiscMagEntity> getAllDiscMags() {
        return discMagRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscMagEntity> getDiscMagById(@PathVariable Long id) {
        return discMagRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public DiscMagEntity addDiscMag(@RequestBody DiscMagEntity discMag) {
        return discMagRepository.save(discMag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscMagEntity> updateDiscMag(
            @PathVariable Long id,
            @RequestBody DiscMagEntity updatedDiscMag) {

        return discMagRepository.findById(id)
                .map(discMag -> {
                    discMag.setCopies(updatedDiscMag.getCopies());
                    discMag.setCurrentIssue(updatedDiscMag.getCurrentIssue());
                    discMag.setName(updatedDiscMag.getName());
                    discMag.setPrice(updatedDiscMag.getPrice());
                    discMag.setTitle(updatedDiscMag.getTitle());
                    return ResponseEntity.ok(discMagRepository.save(discMag));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscMag(@PathVariable Long id) {
        if (!discMagRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        discMagRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
