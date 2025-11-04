package fitpass.fitpass.model.dto;

import fitpass.fitpass.model.entity.WorkDay;
import fitpass.fitpass.model.entity.enums.DayOfWeek;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class WorkDayDTO {
    private Long id;
    private LocalDate validFrom;
    private DayOfWeek day;
    private LocalTime fromTime;
    private LocalTime untilTime;
//    private Boolean isDeleted = false;

    public WorkDayDTO(WorkDay workDay){
        this.id = workDay.getId();
        this.validFrom = workDay.getValidFrom();
        this.day = workDay.getDayOfWeek();
        this.fromTime = workDay.getFrom();
        this.untilTime = workDay.getUntil();
//        this.isDeleted  = workDay.getIsDeleted();
    }
}
