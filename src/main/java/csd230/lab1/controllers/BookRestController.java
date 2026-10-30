package csd230.lab1.controllers;


import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.CartEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rest/books")
public class BookRestController {
    @Autowired
    private BookEntityRepository bookRepository;
    @Autowired
    private CartEntityRepository cartRepository;

    @GetMapping
    public List<BookEntity> getAllBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return bookRepository.findAll(); // Looks for bookList.html in templates
    }

    @GetMapping("/{id}")
    public Optional<BookEntity> getBookById(@PathVariable Long id, Model model) {
        BookEntity book = bookRepository.findById(id).orElse(null);
        model.addAttribute("book", book);
        return bookRepository.findById(id);
    }

//    @GetMapping("/add")
//    public String addBookForm(Model model) {
//        model.addAttribute("book", new BookEntity());
//        return bookRepository.save(model);
//    }   gotta figure out the syntax, brain no work atm


}
