package csd230.lab1.controllers;
import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.CartEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rest/books")
public class BookRestController {
    @Autowired
    private BookEntityRepository bookRepository;
//    @Autowired
//    private CartEntityRepository cartRepository;

    @GetMapping
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<BookEntity> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id);
    }

//    @GetMapping("/add")
//    public Model addBookForm(Model model) {
//        return model.addAttribute("book", new BookEntity());
//    }

    @PostMapping("/add")
    public BookEntity addBook(@ModelAttribute BookEntity book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookEntity> updateBook(
            @PathVariable Long id,
            @RequestBody BookEntity updatedBook) {

        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setPrice(updatedBook.getPrice());
                    return ResponseEntity.ok(bookRepository.save(book));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
