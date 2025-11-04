package fitpass.fitpass.model.dto;

import fitpass.fitpass.model.entity.Discipline;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class DisciplineDTO {
    private Long id;
    private String name;
//    private Boolean isDeleted = false;

    public DisciplineDTO(Discipline discipline) {
        this.id = discipline.getId();
        this.name = discipline.getName();
//        this.isDeleted = discipline.getIsDeleted();
    }
}
