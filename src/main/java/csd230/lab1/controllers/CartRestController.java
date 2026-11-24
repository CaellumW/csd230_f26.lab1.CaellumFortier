package csd230.lab1.controllers;

import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Iterator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import csd230.lab1.repositories.CartEntityRepository;
import csd230.lab1.entities.UserEntity;
import csd230.lab1.repositories.UserEntityRepository;
import org.springframework.web.server.ResponseStatusException;
import csd230.lab1.repositories.ProductEntityRepository;

@RestController
@RequestMapping("/api/rest/cart")
@Tag(name = "Carts", description = "Operations for managing carts! =)")

public class CartRestController {
    @Autowired
    private BookEntityRepository bookRepository;
    @Autowired
    private PublicationEntityRepository publicationRepository;
    @Autowired
    private OrderEntityRepository orderRepository;

    private final CartEntityRepository repository;
    private final UserEntityRepository userRepository;
    private final ProductEntityRepository productRepository;

    public CartRestController(CartEntityRepository repository, UserEntityRepository userRepository, ProductEntityRepository productRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    private CartEntity getCartForCurrentUser(Principal principal) {
        String username = principal.getName();
        UserEntity user = userRepository.findByUsername(username);
        CartEntity cart = repository.findByUser(user);

        if (cart == null) {
            cart = new CartEntity();
            cart.setUser(user);
            repository.save(cart);
        }
        return cart;
    }

    @PostMapping("/mine/products/{productId}")
    @Transactional
    @Operation(summary = "Add an existing product to the signed-in user's cart")
    public CartEntity addMine(@PathVariable Long productId, Principal principal) {
        var user = currentUser(principal, true);
        var product = productRepository.findById(productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        CartEntity cart = repository.findByUser(user);
        if (cart == null) {
            cart = new CartEntity();
            cart.setUser(user);
        }
        cart.addProduct(product);
        return repository.save(cart);
    }

    // The authenticated username is the owner; no client cart/user ID is accepted.
    private UserEntity currentUser(Principal principal, boolean forUpdate) {
        var user = principal == null ? null : forUpdate
                ? userRepository.findForCartUpdate(principal.getName())
                : userRepository.findByUsername(principal.getName());
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED);
        }
        return user;
    }

    @GetMapping("/mine")
    @Transactional(readOnly = true)
    @Operation(summary = "Read the signed-in user's cart; 204 if none has been created")
    public ResponseEntity<CartEntity> mine(Principal principal) {
        CartEntity cart = repository.findByUser(currentUser(principal, false));
        return cart == null ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cart);
    }

    @DeleteMapping("/mine/products/{productId}")
    @Transactional
    @Operation(summary = "Remove a product from the signed-in user's cart")
    public ResponseEntity<CartEntity> removeMine(
            @PathVariable Long productId, Principal principal) {
        var user = currentUser(principal, true);
        var product = productRepository.findById(productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        CartEntity cart = repository.findByUser(user);
        if (cart == null) {
            return ResponseEntity.noContent().build();
        }
        cart.getProducts().remove(product);
        product.getCarts().remove(cart);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/PUT/{bookId}")
    @Operation(description = "Adds a book to the cart by the book's ID")
    @ApiResponse(responseCode = "200", description = "Book added to cart")
    public CartEntity addToCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if (book != null) {
            cart.addProduct(book);
            return repository.save(cart);
        }
        return cart;
    }

    @DeleteMapping("/DELETE/{bookId}")
    @Operation(description = "Deletes a book from the cart by the book's ID")
    @ApiResponse(responseCode = "200", description = "Book deleted")
    public CartEntity removeFromCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        if (book != null) {
            cart.getProducts().remove(book);
            return repository.save(cart);
        }
        return cart;
    }

    @PostMapping("/checkout")
    @Operation(description = "Turns the existing cart into an order")
    @ApiResponse(responseCode = "200", description = "Checkout successful")
    public Long checkout(Principal principal) {

        CartEntity cart = getCartForCurrentUser(principal);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        OrderEntity order = new OrderEntity();
        Double total = 0.0;

        Iterator<ProductEntity> iterator = cart.getProducts().iterator();

        while (iterator.hasNext()) {
            ProductEntity product = iterator.next();
            if (product instanceof PublicationEntity publication) {
                if (publication.getCopies() <= 0) {
                    iterator.remove();
                    continue;
                }
                publication.setCopies(publication.getCopies() - 1);
                publicationRepository.save(publication);
            }
            total += product.getPrice();
            order.getProducts().add(product);
        }

        order.setTotalAmount(total);
        orderRepository.save(order);
        repository.save(cart);

        return order.getId();
    }
}
