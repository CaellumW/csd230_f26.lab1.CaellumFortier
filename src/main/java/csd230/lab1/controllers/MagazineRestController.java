package csd230.lab1.controllers;
import csd230.lab1.entities.MagazineEntity;
import csd230.lab1.repositories.MagazineEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api/rest/magazines")
public class MagazineRestController {
    @Autowired
    private MagazineEntityRepository magazineRepository;

    @GetMapping("/")
    public List<MagazineEntity> getAllMags() {
        return magazineRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MagazineEntity> getMagazineById(@PathVariable Long id) {
        return magazineRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public MagazineEntity addMagazine(@RequestBody MagazineEntity magazine) {
        return magazineRepository.save(magazine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MagazineEntity> updateMagazine(
            @PathVariable Long id,
            @RequestBody MagazineEntity updatedMagazine) {

        return magazineRepository.findById(id)
                .map(magazine -> {
                    magazine.setCurrentIssue(updatedMagazine.getCurrentIssue());
                    magazine.setCopies(updatedMagazine.getCopies());
                    magazine.setName(updatedMagazine.getName());
                    magazine.setPrice(updatedMagazine.getPrice());
                    return ResponseEntity.ok(magazineRepository.save(magazine));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMagazine(@PathVariable Long id) {
        if (!magazineRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        magazineRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
