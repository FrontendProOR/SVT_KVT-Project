package fitpass.fitpass.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fromDateTime", nullable = false)
    private LocalDateTime from;

    @Column(name = "untilDateTime", nullable = false)
    private LocalDateTime until;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User wentBy;

//    @Column(name = "is_deleted",nullable = false)
//    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", referencedColumnName = "id")
    @JsonBackReference
    private Facility atFacility;

    public Exercise(LocalDateTime fromDate, LocalDateTime untilDate, User user, Facility facility) {
        this.from = fromDate;
        this.until = untilDate;
        this.wentBy = user;
        this.atFacility = facility;
//        this.isDeleted = false;
    }
}