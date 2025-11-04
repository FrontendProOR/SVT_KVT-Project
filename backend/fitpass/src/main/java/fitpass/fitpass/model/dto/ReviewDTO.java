package fitpass.fitpass.model.dto;

import fitpass.fitpass.model.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {

    private Long id;
    private LocalDateTime createdAt;
    private boolean isHidden;
    private Integer exerciseCount;
    private boolean active;
    private UserDTO user;
    private CommentDTO comment;
    private RateDTO rate;
    private Long facilityId;
//    private Boolean isDeleted = false;

    public ReviewDTO(Review review){
        this.id = review.getId();
        this.isHidden = review.isHidden();
        this.active = review.isHidden();
        this.createdAt = review.getCreatedAt();
        this.exerciseCount = review.getExcerciseCount();
        this.user = new UserDTO(review.getMadeBy());
        if(review.getLeftA() != null){
            this.comment = new CommentDTO(review.getLeftA());
        }
        this.rate = new RateDTO(review.getWithRating());
        this.facilityId = review.getBelongsTo().getId();
//        this.isDeleted = review.getIsDeleted();
    }

}
