package fitpass.fitpass.model.dto;

import fitpass.fitpass.model.entity.Facility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import fitpass.fitpass.model.entity.Rate;

@Getter
@Setter
@NoArgsConstructor
public class RateDTO {

    private Long id;
    private Integer equipment;
    private Integer staff;
    private Integer hygene;
    private Integer space;
    private Integer facilityId;
//    private Integer facility;
//    private Boolean isDeleted = false;

    public RateDTO(Rate rate){
        this.id = rate.getId();
        this.equipment = rate.getEquipment();
        this.staff = rate.getStaff();
        this.hygene = rate.getHygene();
        this.space = rate.getSpace();
        this.facilityId = Integer.parseInt(String.valueOf(rate.getFacility().getId()));
//        this.facility = Integer.parseInt(String.valueOf(rate.getFacility())) ;
//        this.isDeleted = rate.getIsDeleted();
    }

}

