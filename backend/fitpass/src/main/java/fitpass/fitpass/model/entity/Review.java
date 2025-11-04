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
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Integer excerciseCount;

    @Column(nullable = false)
    private boolean hidden;

    @ManyToOne()
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    @JsonBackReference
    private User madeBy;

    @OneToOne()
    @JoinColumn(name = "comment_id" , referencedColumnName = "id")
    @JsonBackReference
    private Comment leftA;

    @ManyToOne()
    @JoinColumn(name = "facility_id" , referencedColumnName = "id")
    @JsonBackReference
    private Facility belongsTo;

    @OneToOne()
    @JoinColumn(name = "rate_id" , referencedColumnName = "id")
    @JsonBackReference
    private Rate withRating;

//    @Column(name = "is_deleted",nullable = false)
//    private Boolean isDeleted = false;
}
