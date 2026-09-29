package csd230.lab1;

import csd230.lab1.entities.ProductEntity;
import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.ProductEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductEntityRepositoryDerivedQueryTest {

    @Autowired
    private ProductEntityRepository productRepo;

    @Test
    void testFindByTitleLike() {
        BookEntity book1 = new BookEntity("Java Programming", 25.00, 10, "Author A");
        BookEntity book2 = new BookEntity("Advanced Java", 30.00, 5, "Author B");
        productRepo.save(book1);
        productRepo.save(book2);

        List<ProductEntity> results = productRepo.findByTitleLike("%Java%");

        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    void testFindProductsInPriceRange() {
        BookEntity cheap = new BookEntity("Cheap Book", 10.00, 5, "Author C");
        BookEntity mid = new BookEntity("Mid Book", 20.00, 5, "Author D");
        BookEntity expensive = new BookEntity("Expensive Book", 50.00, 5, "Author E");

        productRepo.save(cheap);
        productRepo.save(mid);
        productRepo.save(expensive);

        List<ProductEntity> results = productRepo.findProductsInPriceRange(15.00, 40.00);

        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("Mid Book");
    }
}
