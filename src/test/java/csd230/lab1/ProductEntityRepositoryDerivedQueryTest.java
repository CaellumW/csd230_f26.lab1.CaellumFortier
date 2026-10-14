package csd230.lab1;

import csd230.lab1.entities.ProductEntity;
import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.ProductEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductEntityRepositoryDerivedQueryTest {

    @Autowired
    private BookEntityRepository bookRepo;

    @Test
    void testFindByTitleLike() {
        BookEntity book1 = new BookEntity("Java Programming", "Author A", 25.00, 10);
        BookEntity book2 = new BookEntity("Advanced Java", "Author B", 30.00, 5);
        bookRepo.save(book1);
        bookRepo.save(book2);

        List<ProductEntity> results = bookRepo.findByTitleLike("%Java%");

        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    void testFindProductsInPriceRange() {
        BookEntity cheap = new BookEntity("Cheap Book", "Author C", 10.00, 5);
        BookEntity mid = new BookEntity("Mid Book", "Author D", 20.00, 5);
        BookEntity expensive = new BookEntity("Expensive Book", "Author E", 50.00, 5);

        bookRepo.save(cheap);
        bookRepo.save(mid);
        bookRepo.save(expensive);

        List<ProductEntity> results = bookRepo.findProductsInPriceRange(15.00, 40.00);

        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("Mid Book");
    }
}
