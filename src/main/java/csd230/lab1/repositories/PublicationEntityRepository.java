package csd230.lab1.repositories;
import csd230.lab1.entities.PublicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationEntityRepository extends JpaRepository<PublicationEntity, Long> {
    List<PublicationEntity> findByTitleLike(String title);
}
