package fitpass.fitpass.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import fitpass.fitpass.model.entity.Exercise;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseDTO {

    private LocalDateTime fromDate;
    private LocalDateTime untilDate;
    private Long facilityId;
    private Long userId;
//    private Boolean isDeleted = false;

    public ExerciseDTO(Exercise exercise){
        this.fromDate = exercise.getFrom();
        this.untilDate = exercise.getUntil();
        this.facilityId = exercise.getAtFacility().getId();
        this.userId = exercise.getWentBy().getId();
//        this.isDeleted = false;
    }

}