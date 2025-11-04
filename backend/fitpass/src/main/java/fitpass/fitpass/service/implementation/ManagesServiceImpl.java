package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.dto.ManagesDTO;
import fitpass.fitpass.model.entity.Facility;
import fitpass.fitpass.model.entity.Manages;
import fitpass.fitpass.model.entity.User;
import fitpass.fitpass.repository.ManagesRepository;
import fitpass.fitpass.service.FacilityService;
import fitpass.fitpass.service.ManagesService;
import fitpass.fitpass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ManagesServiceImpl implements ManagesService {
    @Autowired
    private ManagesRepository managesRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FacilityService facilityService;

    @Override
    public Manages create(ManagesDTO managesDTO) {
        Manages manages = new Manages();
        Optional<Facility> facility = facilityService.findById(managesDTO.getFacilityId());
        Optional<User> user = userService.findById(managesDTO.getUserId());
        if (facility.isPresent()) {
            Facility facilityEntity = facility.get();
            manages.setFacility(facilityEntity);
            if (!facilityEntity.isActive()) {
                facilityEntity.setActive(true);
                facilityService.save(facilityEntity);
            }
        }
        if (user.isPresent()) {
            manages.setUser(user.get());
        }
        manages.setStartDate(LocalDate.now());
        return this.managesRepository.save(manages);
    }

    @Override
    public void delete(ManagesDTO managesDTO) {
        Manages manage = this.findByUserIdAndFacilityId(managesDTO.getUserId(), managesDTO.getFacilityId());
        if (manage != null) {
            this.managesRepository.delete(manage);
            List<Manages> managesList = managesRepository.findAllByFacilityId(managesDTO.getFacilityId());
            if (managesList.isEmpty()) {
                Optional<Facility> facility = facilityService.findById(managesDTO.getFacilityId());
                if (facility.isPresent()) {
                    Facility facilityEntity = facility.get();
                    facilityEntity.setActive(false);
                    facilityService.save(facilityEntity);
                }
            }
        }
    }

    @Override
    public Manages findByUserId(Long userId) {
        return managesRepository.findByUserId(userId);
    }

    @Override
    public List<Manages> findAllByFacilityId(Long facilityId) {
        return managesRepository.findAllByFacilityId(facilityId);
    }

    @Override
    public Manages findByUserIdAndFacilityId(Long userId, Long facilityId) {
        return managesRepository.findByUserIdAndFacilityId(userId, facilityId);
    }
}
