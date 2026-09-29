package csd230.lab1;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.ProductEntity;
import csd230.lab1.repositories.CartEntityRepository;
import csd230.lab1.repositories.ProductEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CartProductRelationshipTest {

    @Autowired
    private CartEntityRepository cartRepo;

    @Autowired
    private ProductEntityRepository productRepo;

    @Test
    void testCartProductRelationship() {

        BookEntity book1 = new BookEntity("Book One", 15.00, 10, "Author A");
        BookEntity book2 = new BookEntity("Book Two", 20.00, 5, "Author B");

        productRepo.save(book1);
        productRepo.save(book2);


        CartEntity cart = new CartEntity();
        cart.addProduct(book1);
        cart.addProduct(book2);

        cartRepo.save(cart);


        CartEntity foundCart = cartRepo.findById(cart.getId()).orElse(null);

        assertThat(foundCart).isNotNull();
        Set<ProductEntity> products = foundCart.getProducts();

        assertThat(products.size()).isEqualTo(2);
        assertThat(products).extracting("title")
                .containsExactlyInAnyOrder("Book One", "Book Two");
    }
}
