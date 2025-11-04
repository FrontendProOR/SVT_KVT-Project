package fitpass.fitpass.service;

import fitpass.fitpass.model.entity.Image;
import java.util.Optional;
import java.util.Set;

public interface ImageService {
    Image save(Image image);
    void deleteById(Long id);
    void deleteAllById(Set<Image> images);
    Optional<Image> findByPath(String path);
    Optional<Image> findById(Long id);
}
