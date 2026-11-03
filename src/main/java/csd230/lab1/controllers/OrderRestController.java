package csd230.lab1.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import csd230.lab1.entities.OrderEntity;
import csd230.lab1.repositories.CartEntityRepository;
import csd230.lab1.repositories.OrderEntityRepository;
import csd230.lab1.repositories.PublicationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/rest/orders")
public class OrderRestController {
    @Autowired
    private OrderEntityRepository orderRepository;
    @Autowired
    private CartEntityRepository cartRepository;
    @Autowired
    private PublicationEntityRepository publicationRepository;

    @GetMapping("/")
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public OrderEntity addOrder(@RequestBody OrderEntity order) {
        return orderRepository.save(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderEntity updatedOrder) {

        return orderRepository.findById(id)
                .map(order -> {
                    order.setOrderDate(updatedOrder.getOrderDate());
                    order.setTotalAmount(updatedOrder.getTotalAmount());
                    return ResponseEntity.ok(orderRepository.save(order));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
