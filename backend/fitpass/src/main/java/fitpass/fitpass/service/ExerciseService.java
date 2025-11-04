package fitpass.fitpass.service;

import fitpass.fitpass.model.dto.ExerciseDTO;
import fitpass.fitpass.model.entity.Exercise;

import java.util.List;

public interface ExerciseService {
    Exercise save(ExerciseDTO exerciseDTO);
    List<ExerciseDTO> findExercisesByUserId(Long userId);
}
