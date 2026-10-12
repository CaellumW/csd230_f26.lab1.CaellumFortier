package csd230.lab1.repositories;
import csd230.lab1.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    static UserEntity findByUsername(String username) {
        return null;
    }
}
