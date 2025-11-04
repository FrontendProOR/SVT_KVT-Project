package fitpass.fitpass.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String path;

//    @Column(name = "is_deleted",nullable = false)
//    private Boolean isDeleted = false;

    @ManyToOne()
    @JsonManagedReference
    @JoinColumn(name = "facility_id" , referencedColumnName = "id")
    private Facility belongsToFacility;
}
