package fitpass.fitpass.service;

import fitpass.fitpass.model.dto.FacilityDTO;
import fitpass.fitpass.model.entity.Facility;

import java.util.List;
import java.util.Optional;

public interface FacilityService {
    Facility createFacility(FacilityDTO facilityDTO);
    FacilityDTO save(Facility facility);
    void deleteById(Long id);
    List<FacilityDTO> findAll();
    Optional<Facility> findByAddress(String adress);
    Optional<Facility> findById(Long id);
    void deleteWorkDaysByFacilityId(Long facilityId);

}
