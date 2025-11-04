package fitpass.fitpass.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "facilities")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column()
    private Double totalRating;

    @Column(nullable = false)
    private boolean active;

//    @Column(name = "is_deleted",nullable = false)
//    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "facility" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Set<Manages> manages = new HashSet<Manages>();

    @OneToMany(mappedBy = "atFacility" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Set<Exercise> exercises = new HashSet<Exercise>();

    @OneToMany(mappedBy = "facility" , fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<WorkDay> workDays = new HashSet<WorkDay>();

    @OneToMany(mappedBy = "byFacility" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Set<Discipline> disciplines = new HashSet<Discipline>();

    @OneToMany(mappedBy = "belongsToFacility" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<Image>();

    @OneToMany(mappedBy = "belongsTo" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<Review>();
}