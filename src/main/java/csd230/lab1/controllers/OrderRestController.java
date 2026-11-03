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
@Tag(name = "Orders", description = "Operations for managing orders! =)")
public class OrderRestController {
    @Autowired
    private OrderEntityRepository orderRepository;
    @Autowired
    private CartEntityRepository cartRepository;
    @Autowired
    private PublicationEntityRepository publicationRepository;

    @GetMapping("/")
    @Operation(description = "Retrieves all orders")
    @ApiResponse(responseCode = "200", description = "All orders found")
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Retrieves a specific order via the order's ID")
    @ApiResponse(responseCode = "200", description = "Order found")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @PostMapping("/add")
    @Operation(description = "Adds a new order to the order repository")
    @ApiResponse(responseCode = "200", description = "Order added")
    public OrderEntity addOrder(@RequestBody OrderEntity order) {
        return orderRepository.save(order);
    }

    @PutMapping("/{id}")
    @Operation(description = "Updates an existing order with new information")
    @ApiResponse(responseCode = "200", description = "Order updated")
    public ResponseEntity<OrderEntity> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderEntity updatedOrder) {

        return orderRepository.findById(id)
                .map(order -> {
                    order.setOrderDate(updatedOrder.getOrderDate());
                    order.setTotalAmount(updatedOrder.getTotalAmount());
                    return ResponseEntity.ok(orderRepository.save(order));
                })
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a specific order via the order's ID")
    @ApiResponse(responseCode = "200", description = "Order deleted")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }

        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
