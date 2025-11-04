package fitpass.fitpass.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import fitpass.fitpass.model.dto.RateDTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ratings")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer equipment;

    @Column(nullable = false)
    private Integer staff;

    @Column(nullable = false)
    private Integer hygene;

    @Column(nullable = false)
    private Integer space;

    //ko je dao ocenu  id i za koji centar id
    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;



//    @Column(name = "is_deleted",nullable = false)
//    private Boolean isDeleted = false;

    public Rate(RateDTO rateDTO) {
        this.id = rateDTO.getId();
        this.equipment = rateDTO.getEquipment();
        this.staff = rateDTO.getStaff();
        this.hygene = rateDTO.getHygene();
        this.space = rateDTO.getSpace();

//        this.isDeleted = rateDTO.getIsDeleted();
    }

}