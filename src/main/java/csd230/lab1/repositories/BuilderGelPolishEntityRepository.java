package csd230.lab1.repositories;

import csd230.lab1.entities.BuilderGelPolishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuilderGelPolishEntityRepository extends JpaRepository<BuilderGelPolishEntity, Long> {
    List<BuilderGelPolishEntity> findByConsistency(String consistency);
}
