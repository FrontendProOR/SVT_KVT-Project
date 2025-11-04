package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.dto.ReviewDTO;
import fitpass.fitpass.model.entity.*;
import fitpass.fitpass.repository.ReviewRepository;
import fitpass.fitpass.service.FacilityService;
import fitpass.fitpass.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    FacilityService facilityService;

    @Override
    public Review create(ReviewDTO reviewDTO) {
        Review review = new Review();
        if(reviewDTO.getComment() != null){
            review.setLeftA(new Comment(reviewDTO.getComment()));
        }
        review.setHidden(reviewDTO.isHidden());
        review.setWithRating(new Rate(reviewDTO.getRate()));
        review.setMadeBy(new User(reviewDTO.getUser()));
        review.setExcerciseCount(reviewDTO.getExerciseCount());
        review.setCreatedAt(LocalDateTime.now());
        Optional<Facility> facility = facilityService.findById(reviewDTO.getFacilityId());
        if(facility.isPresent()){
            review.setBelongsTo(facility.get());
        }
        return reviewRepository.save(review);
    }
}
