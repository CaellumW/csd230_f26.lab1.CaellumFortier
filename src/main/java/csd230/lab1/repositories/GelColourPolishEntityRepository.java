package csd230.lab1.repositories;

import csd230.lab1.entities.GelColourPolishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GelColourPolishEntityRepository extends JpaRepository<GelColourPolishEntity, Long> {
    List<GelColourPolishEntity> findByGlitter(boolean glitter);

    List<GelColourPolishEntity> findByColour(String colour);
}
