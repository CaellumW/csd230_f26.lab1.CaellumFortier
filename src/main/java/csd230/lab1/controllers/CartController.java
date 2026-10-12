package csd230.lab1.controllers;

import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Iterator;


@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartEntityRepository cartRepository;
    @Autowired
    private BookEntityRepository bookRepository;
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private PublicationEntityRepository publicationRepository;
    @Autowired
    private ProductEntityRepository productRepository;
    @Autowired
    private OrderEntityRepository orderRepository;

    private CartEntity getCartForCurrentUser(Principal principal) {
        String username = principal.getName();
        UserEntity user = UserEntityRepository.findByUsername(username);
        CartEntity cart = cartRepository.findByUser(user);

        if (cart == null) {
            cart = new CartEntity();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        return cart;
    }

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        model.addAttribute("cart", cart);
        return "cartDetails";
    }

    @GetMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            cart.addProduct(book);
            cartRepository.save(cart);
        }
        return "redirect:/books";
    }

    @GetMapping("/remove/{bookId}")
    public String removeFromCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book != null) {
            cart.getProducts().remove(book);
            cartRepository.save(cart);
        }
        return "redirect:/cart";
    }


//    am i supposed to have checkout logic here? that's in my order controller

    @PostMapping("/checkout")
    public String checkout(Principal principal) {

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

        return "redirect:/orders/" + order.getId();
    }

}
