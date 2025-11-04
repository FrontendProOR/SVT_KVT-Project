package fitpass.fitpass.repository;

import fitpass.fitpass.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByPath(String path);
    Optional<Image> findById(Long id);
}
