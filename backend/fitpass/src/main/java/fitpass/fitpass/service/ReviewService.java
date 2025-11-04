package fitpass.fitpass.service;

import fitpass.fitpass.model.dto.ReviewDTO;
import fitpass.fitpass.model.entity.Review;

public interface ReviewService {
    Review create(ReviewDTO reviewDTO);
}
