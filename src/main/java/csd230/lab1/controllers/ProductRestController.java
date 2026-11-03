package csd230.lab1.controllers;

import csd230.lab1.entities.ProductEntity;
import csd230.lab1.repositories.ProductEntityRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/rest/products")
@Tag(name = "Products", description = "Operations for managing products! =)")
public class ProductRestController {
    @Autowired
    private ProductEntityRepository productRepo;

    @GetMapping("/")
    public List<ProductEntity> getAllProducts() {
        return productRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getOrderById(@PathVariable Long id) {
        return productRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ProductEntity addProduct(@RequestBody ProductEntity product) {
        return productRepo.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductEntity updatedProduct) {

        return productRepo.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    return ResponseEntity.ok(productRepo.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!productRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        productRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
