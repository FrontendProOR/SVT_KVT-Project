package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.entity.Image;
import fitpass.fitpass.repository.ImageRepository;
import fitpass.fitpass.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Override
    public Optional<Image> findByPath(String path) {
        return this.imageRepository.findByPath(path);
    }

    @Override
    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image save(Image image) {
        return this.imageRepository.save(image);
    }

    @Override
    public void deleteById(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public void deleteAllById(Set<Image> images) {
        for(Image image:images){
            deleteById(image.getId());
        }
    }
}
