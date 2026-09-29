package csd230.lab1.repositories;

import csd230.lab1.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByTitleLike(String title);

    @Query("SELECT p FROM ProductEntity p WHERE p.price BETWEEN :min AND :max")
    List<ProductEntity> findProductsInPriceRange(double min, double max);

}
