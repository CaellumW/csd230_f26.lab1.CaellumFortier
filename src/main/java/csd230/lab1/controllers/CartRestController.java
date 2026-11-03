package csd230.lab1.controllers;

import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Iterator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/rest/cart")
@Tag(name = "Carts", description = "Operations for managing carts! =)")
public class CartRestController {
    @Autowired
    private CartEntityRepository cartRepository;
    @Autowired
    private BookEntityRepository bookRepository;
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private PublicationEntityRepository publicationRepository;
    @Autowired
    private OrderEntityRepository orderRepository;

    private CartEntity getCartForCurrentUser(Principal principal) {
        String username = principal.getName();
        UserEntity user = userRepository.findByUsername(username);
        CartEntity cart = cartRepository.findByUser(user);

        if (cart == null) {
            cart = new CartEntity();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        return cart;
    }

    @PutMapping("/PUT/{bookId}")
    @Operation(description = "Adds a book to the cart by the book's ID")
    public CartEntity addToCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            cart.addProduct(book);
            return cartRepository.save(cart);
        }
        return cart;
    }

    @DeleteMapping("/DELETE/{bookId}")
    @Operation(description = "Deletes a book from the cart by the book's ID")
    public CartEntity removeFromCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book != null) {
            cart.getProducts().remove(book);
            return cartRepository.save(cart);
        }
        return cart;
    }

    @PostMapping("/checkout")
    @Operation(description = "Turns the existing cart into an order")
    public Long checkout(Principal principal) {

        CartEntity cart = getCartForCurrentUser(principal);
        if (cart == null) {
            throw new RuntimeException("Cart not found for user");
        }
        OrderEntity order = new OrderEntity();
        double total = 0.0;

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
        cartRepository.save(cart);

        return order.getId();
    }
}
