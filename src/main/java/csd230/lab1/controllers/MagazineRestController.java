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
@Tag(name = "Magazines", description = "Operations for managing magazines! =)")
public class MagazineRestController {
    @Autowired
    private MagazineEntityRepository magazineRepository;

    @GetMapping("/")
    @Operation(description = "Lists all of the magazines")
    @ApiResponse(responseCode = "200", description = "All magazines found")
    public List<MagazineEntity> getAllMags() {
        return magazineRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Gets a specific magazine by its ID")
    @ApiResponse(responseCode = "200", description = "Magazine found")
    public ResponseEntity<MagazineEntity> getMagazineById(@PathVariable Long id) {
        return magazineRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    @Operation(description = "Saves a new magazine to the magazine repository")
    @ApiResponse(responseCode = "200", description = "Magazine saved")
    public MagazineEntity addMagazine(@RequestBody MagazineEntity magazine) {
        return magazineRepository.save(magazine);
    }

    @PutMapping("/{id}")
    @Operation(description = "Updates an existing magazine with new information")
    @ApiResponse(responseCode = "200", description = "Magazine updated")
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
    @Operation(description = "Deletes a specific magazine by its ID")
    @ApiResponse(responseCode = "200", description = "Magazine deleted")
    public ResponseEntity<Void> deleteMagazine(@PathVariable Long id) {
        if (!magazineRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        magazineRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
