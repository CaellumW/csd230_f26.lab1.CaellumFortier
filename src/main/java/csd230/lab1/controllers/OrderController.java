package csd230.lab1.controllers;

import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.OrderEntity;
import csd230.lab1.entities.ProductEntity;
import csd230.lab1.entities.PublicationEntity;
import csd230.lab1.repositories.CartEntityRepository;
import csd230.lab1.repositories.OrderEntityRepository;
import csd230.lab1.repositories.ProductEntityRepository;
import csd230.lab1.repositories.PublicationEntityRepository;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Iterator;


@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderEntityRepository orderRepository;
    @Autowired
    private CartEntityRepository cartRepository;
    @Autowired
    private ProductEntityRepository productRepository;
    @Autowired
    private PublicationEntityRepository publicationRepository;

    @GetMapping
    public String getAllCart(Model model) {
        model.addAttribute("cart", cartRepository.findAll());
        return "cartList";
    }

    @GetMapping("/checkout/{cartId}")
    public String checkout(@PathVariable Long cartId, Model model) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

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

        model.addAttribute("order", order);
        return "redirect:/order/" + order.getId();
    }

    @GetMapping("/{orderId}")
    public String viewOrder(@PathVariable Long orderId, Model model) {

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        model.addAttribute("order", order);
        return "orderDetails";
    }


}
