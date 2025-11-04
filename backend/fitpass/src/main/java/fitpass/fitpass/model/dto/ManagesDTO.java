package fitpass.fitpass.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import fitpass.fitpass.model.entity.Manages;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ManagesDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;
    private Long facilityId;
//    private  Boolean isDeleted = false;

    public ManagesDTO(Manages manages){
        this.id = manages.getId();
        this.startDate = manages.getStartDate();
        if(manages.getEndDate() != null){
            this.endDate = manages.getEndDate();
        }
        this.userId = manages.getUser().getId();
        this.facilityId = manages.getFacility().getId();
//        this.isDeleted = manages.getIsDeleted();
    }

}