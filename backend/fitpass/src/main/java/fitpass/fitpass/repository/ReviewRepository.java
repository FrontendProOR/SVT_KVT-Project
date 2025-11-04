package fitpass.fitpass.repository;

import fitpass.fitpass.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
}
