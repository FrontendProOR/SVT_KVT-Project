package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.dto.ExerciseDTO;
import fitpass.fitpass.model.entity.Exercise;
import fitpass.fitpass.model.entity.Facility;
import fitpass.fitpass.model.entity.User;
import fitpass.fitpass.model.entity.WorkDay;
import fitpass.fitpass.repository.ExerciseRepository;
import fitpass.fitpass.service.ExerciseService;
import fitpass.fitpass.service.FacilityService;
import fitpass.fitpass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FacilityService facilityService;

    @Override
    public Exercise save(ExerciseDTO exerciseDTO) {
        User user = userService.findById(exerciseDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Facility facility = facilityService.findById(exerciseDTO.getFacilityId())
                .orElseThrow(() -> new IllegalArgumentException("Facility not found"));

        LocalDateTime exerciseStartDateTime = exerciseDTO.getFromDate();
        LocalDateTime exerciseEndDateTime = exerciseDTO.getUntilDate();
        boolean isMatchingWorkdayFound = false;

        Set<WorkDay> facilityWorkDays = facility.getWorkDays();

        for (WorkDay workDay : facilityWorkDays) {
            LocalDateTime workDayStartDateTime = LocalDateTime.of(workDay.getValidFrom(), workDay.getFrom());
            LocalDateTime workDayEndDateTime = LocalDateTime.of(workDay.getValidFrom(), workDay.getUntil());

            // Check for overlapping time slot on the same day
            if (!workDayEndDateTime.toLocalTime().isBefore(exerciseStartDateTime.toLocalTime()) &&
                    !workDayStartDateTime.toLocalTime().isAfter(exerciseEndDateTime.toLocalTime())) {
                isMatchingWorkdayFound = true;
                break;
            }
        }

        if (isMatchingWorkdayFound) {
            Exercise exercise = new Exercise(exerciseStartDateTime, exerciseEndDateTime, user, facility);
            exerciseRepository.save(exercise);
            return exercise;
        } else {
            throw new IllegalArgumentException("No matching workday found for the given exercise dates and times.");
        }
    }
    @Override
    public List<ExerciseDTO> findExercisesByUserId(Long userId) {
        List<Exercise> exercises = exerciseRepository.myExercises(userId);
        return exercises.stream().map(ExerciseDTO::new).collect(Collectors.toList());
    }
}
