package fitpass.fitpass.controller;

import fitpass.fitpass.model.dto.ExerciseDTO;
import fitpass.fitpass.model.entity.Exercise;
import fitpass.fitpass.model.entity.Facility;
import fitpass.fitpass.model.entity.User;
import fitpass.fitpass.security.TokenUtils;
import fitpass.fitpass.service.ExerciseService;
import fitpass.fitpass.service.FacilityService;
import fitpass.fitpass.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/exercises")
public class ExerciseController {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    UserService userService;

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    FacilityService facilityService;

    @PostMapping
    public ResponseEntity<ExerciseDTO> create(HttpServletRequest request, @RequestBody ExerciseDTO exerciseDto){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        if(token == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String email = tokenUtils.getEmailFromToken(token);
        User user = userService.findByEmail(email);

        if(user == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        if(exerciseDto == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        Optional<Facility> facility = facilityService.findById(exerciseDto.getFacilityId());
        if(facility.isPresent()){
            if(!(facility.get().isActive())){
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }
        }
        exerciseDto.setFromDate(exerciseDto.getFromDate().plusHours(2));
        exerciseDto.setUntilDate(exerciseDto.getUntilDate().plusHours(2));
        exerciseDto.setUserId(user.getId());

        Exercise exercise = exerciseService.save(exerciseDto);
        if(exercise == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        ExerciseDTO exerciseDtoResponse = new ExerciseDTO(exercise);
        return new ResponseEntity<>(exerciseDtoResponse, HttpStatus.CREATED);
    }

    @GetMapping("/user-exercises")
    public ResponseEntity<List<ExerciseDTO>> getUserExercises(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = tokenUtils.getEmailFromToken(token);
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ExerciseDTO> exercises = exerciseService.findExercisesByUserId(user.getId());
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

}
