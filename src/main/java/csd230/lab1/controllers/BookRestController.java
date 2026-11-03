package csd230.lab1.controllers;
import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.controllers.BookNotFoundException.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    @ApiResponse(responseCode = "200", description = "All books found")
    @ApiResponse(responseCode = "404", description = "Books not able to be found")
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Finds a specific book by its ID")
    @ApiResponse(responseCode = "200", description = "Book found!")
    @ApiResponse(responseCode = "404", description = "Book not found")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PostMapping("/add")
    @Operation(description = "Adds a *pre made* book object")
    @ApiResponse(responseCode = "200", description = "Book added")
    public BookEntity addBook(@RequestBody BookEntity book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    @Operation(description = "Updates and existing book with new information")
    @ApiResponse(responseCode = "200", description = "Book successfully updates")
    public ResponseEntity<BookEntity> updateBook(
            @PathVariable Long id,
            @RequestBody BookEntity updatedBook) throws BookNotFoundException {

        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setPrice(updatedBook.getPrice());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setCopies(updatedBook.getCopies());
                    return ResponseEntity.ok(bookRepository.save(book));
                })
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a specific book using its ID")
    @ApiResponse(responseCode = "200", description = "Book deleted.")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) throws BookNotFoundException {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
