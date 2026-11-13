package csd230.lab1.repositories;

import csd230.lab1.entities.GelPolishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GelPolishEntityRepository extends JpaRepository<GelPolishEntity, Long> {
    List<GelPolishEntity> findByGelType(String gelType);
}
