package fitpass.fitpass.controller;

import fitpass.fitpass.model.dto.FacilityDTO;
import fitpass.fitpass.model.entity.Facility;
import fitpass.fitpass.service.FacilityService;
import fitpass.fitpass.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/facilities")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<FacilityDTO> getOne(@PathVariable Long id){
        Optional<Facility> facility= facilityService.findById(id);
        if(!facility.isPresent()){
            return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
        }
        FacilityDTO facilityDto = new FacilityDTO(facility.get());
        return new ResponseEntity<>(facilityDto,HttpStatus.ACCEPTED);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacilityDTO> createFacility(
            @RequestBody @Validated FacilityDTO facilityDto, HttpServletResponse response) {

        Optional<Facility> facility = facilityService.findByAddress(facilityDto.getAddress());
        if (facility.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (facilityDto.getDisciplines() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (facilityDto.getWorkDays() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Facility facility1 = facilityService.createFacility(facilityDto);
        FacilityDTO facilityDto2 = new FacilityDTO(facility1);
        return new ResponseEntity<>(facilityDto2, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacilityDTO> updateFacility(@RequestBody @Validated FacilityDTO facilityDto) {
        Optional<Facility> facility = facilityService.findById(facilityDto.getId());
        if(!facility.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Facility createdFacility = facilityService.createFacility(facilityDto);
        FacilityDTO facilityDto2 = new FacilityDTO(createdFacility);
        return new ResponseEntity<>(facilityDto2, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        try {
            facilityService.deleteById(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content status
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if the facility doesn't exist
        }
    }


    @GetMapping
    public ResponseEntity<List<FacilityDTO>> getAll() {
        List<FacilityDTO> facilities = facilityService.findAll();
        if (facilities.isEmpty()) {
            return ResponseEntity.ok().body(new ArrayList<>());
        }
        return ResponseEntity.ok().body(facilities);
    }
}
