package csd230.lab1.controllers;
import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api/rest/books")
@Tag(name = "Books", description = "Operations for managing books! =)")
public class BookRestController {
    @Autowired
    private BookEntityRepository bookRepository;

    @GetMapping
    @Operation(description = "Retrieves all books")
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Finds a specific book by its ID")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    @Operation(description = "Adds a *pre made* book object")
    public BookEntity addBook(@RequestBody BookEntity book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    @Operation(description = "Updates and existing book with new information")
    public ResponseEntity<BookEntity> updateBook(
            @PathVariable Long id,
            @RequestBody BookEntity updatedBook) {

        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setPrice(updatedBook.getPrice());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setCopies(updatedBook.getCopies());
                    return ResponseEntity.ok(bookRepository.save(book));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a specific book using its ID")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
