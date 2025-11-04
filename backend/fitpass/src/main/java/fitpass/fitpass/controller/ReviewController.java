package fitpass.fitpass.controller;

import fitpass.fitpass.model.dto.ReviewDTO;
import fitpass.fitpass.model.entity.Review;
import fitpass.fitpass.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping
    ResponseEntity<ReviewDTO> create(@RequestBody @Validated ReviewDTO reviewDto){
        Review review = reviewService.create(reviewDto);
        if(review == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        ReviewDTO reviewDto1 = new ReviewDTO(review);
        return new ResponseEntity<>(reviewDto1,HttpStatus.OK);
    }
}
