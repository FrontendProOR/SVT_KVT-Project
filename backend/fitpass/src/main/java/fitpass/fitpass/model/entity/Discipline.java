package fitpass.fitpass.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "disciplines")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

//    @Column(name = "is_deleted",nullable = false)
//    private Boolean isDeleted = false;

    @ManyToOne()
    @JoinColumn(name = "facility_id" , referencedColumnName = "id")
    private Facility byFacility;
}
