package csd230.lab1;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.ProductEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductEntityRepositoryCrudTest {

    @Autowired
    private ProductEntityRepository productRepo;

    @Test
    void testCrudOperations() {
        // create - C
        BookEntity book = new BookEntity("Test Book", 19.99, 5, "Author");
        productRepo.save(book);

        assertThat(book.getId()).isNotNull();

        // read - R
        BookEntity found = (BookEntity) productRepo.findById(book.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Test Book");

        // update - U
        found.setPrice(29.99);
        productRepo.save(found);

        BookEntity updated = (BookEntity) productRepo.findById(book.getId()).orElse(null);
        assertThat(updated.getPrice()).isEqualTo(29.99);

        // delete - D
        productRepo.delete(updated);
        assertThat(productRepo.findById(book.getId())).isEmpty();

//        wow crud ooh ahh
    }
}
