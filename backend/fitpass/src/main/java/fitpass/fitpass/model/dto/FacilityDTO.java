package fitpass.fitpass.model.dto;

import fitpass.fitpass.model.entity.Facility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class FacilityDTO {
    private Long id;
    private String facilityName;
    private String description;
    private LocalDate createdAt;
    private String address;
    private String city;
    private Double totalRating;
    private boolean active;
//    private Boolean isDeleted = false;
    private Set<DisciplineDTO> disciplines = new HashSet<>();
    private Set<WorkDayDTO> workDays = new HashSet<>();
    private Set<ImageDTO> images = new HashSet<>();
    private Set<ReviewDTO> reviews = new HashSet<>();
    private Set<ExerciseDTO> exercises = new HashSet<>();
    private Set<ManagesDTO> manages = new HashSet<>();

    public FacilityDTO(Facility createdFacility){
        this.id = createdFacility.getId();
        this.facilityName = createdFacility.getName();
        this.description = createdFacility.getDescription();
        this.createdAt = createdFacility.getCreatedAt();
        this.address = createdFacility.getAddress();
        this.city = createdFacility.getCity();
        this.totalRating = createdFacility.getTotalRating();
        this.active = createdFacility.isActive();
//        this.isDeleted = createdFacility.getIsDeleted();

        this.disciplines = createdFacility.getDisciplines().stream()
                .map(DisciplineDTO::new)
                .collect(Collectors.toSet());

        this.workDays = createdFacility.getWorkDays().stream()
                .map(WorkDayDTO::new)
                .collect(Collectors.toSet());

        this.images = createdFacility.getImages().stream()
                .map(ImageDTO::new)
                .collect(Collectors.toSet());

        this.reviews = createdFacility.getReviews().stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toSet());

        this.exercises = createdFacility.getExercises().stream()
                .map(ExerciseDTO::new)
                .collect(Collectors.toSet());

        this.manages = createdFacility.getManages().stream()
                .map(ManagesDTO::new)
                .collect(Collectors.toSet());
    }
}
