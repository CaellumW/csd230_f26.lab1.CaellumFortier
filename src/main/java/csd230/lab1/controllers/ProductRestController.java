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
    @Operation(description = "Retrieves all products")
    @ApiResponse(responseCode = "200", description = "All products found")
    public List<ProductEntity> getAllProducts() {
        return productRepo.findAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Retrieves a specific product via that product's ID")
    @ApiResponse(responseCode = "200", description = "Product found")
    public ResponseEntity<ProductEntity> getOrderById(@PathVariable Long id) {
        return productRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/add")
    @Operation(description = "Adds a new product to the product repository")
    @ApiResponse(responseCode = "200", description = "Product added to the repository")
    public ProductEntity addProduct(@RequestBody ProductEntity product) {
        return productRepo.save(product);
    }

    @PutMapping("/{id}")
    @Operation(description = "Updates an existing product with new product information")
    @ApiResponse(responseCode = "200", description = "Product updated")
    public ResponseEntity<ProductEntity> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductEntity updatedProduct) {

        return productRepo.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    return ResponseEntity.ok(productRepo.save(product));
                })
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a specific product via that product's ID")
    @ApiResponse(responseCode = "200", description = "Product deleted")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!productRepo.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        productRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
