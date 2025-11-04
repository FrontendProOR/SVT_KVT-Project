package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.dto.*;
import fitpass.fitpass.model.entity.*;
import fitpass.fitpass.repository.FacilityRepository;
import fitpass.fitpass.repository.WorkDayRepository;
import fitpass.fitpass.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class FacilityServiceImpl implements FacilityService {
    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private WorkDayService workDayService;

    @Autowired
    private DisciplineService disciplineService;

    @Autowired
    ImageService imageService;

    @Autowired
    private WorkDayRepository workDayRepository;

    @Autowired
    RateService rateService;

    @Override
    public Optional<Facility> findById(Long id) {
        return facilityRepository.findById(id);
    }

    @Override
    public Facility createFacility(FacilityDTO facilityDTO) {
        Facility facility = new Facility();
        Optional<Facility> existingFacilityOptional = Optional.empty();

        if (facilityDTO.getId() != null) {
            existingFacilityOptional = facilityRepository.findById(facilityDTO.getId());
        }

        if (existingFacilityOptional.isPresent()) {
            facility.setId(facilityDTO.getId());
        }

        facility.setName(facilityDTO.getFacilityName());
        facility.setDescription(facilityDTO.getDescription());
        facility.setAddress(facilityDTO.getAddress());
        facility.setCreatedAt(LocalDate.now());
        facility.setCity(facilityDTO.getCity());
        facility.setActive(true);

        if (facilityDTO.getDisciplines() != null) {
            Set<Discipline> newDisciplines = new HashSet<>();
            for (DisciplineDTO disDTO : facilityDTO.getDisciplines()) {
                Discipline discipline = mapToDiscipline(disDTO, facility);
                newDisciplines.add(discipline);
            }
            facility.setDisciplines(newDisciplines);
        }

        if (existingFacilityOptional.isPresent()) {
            Facility existingFacility = existingFacilityOptional.get();
            Set<Discipline> disciplinesToRemove = new HashSet<>();
            for (Discipline existingDiscipline : existingFacility.getDisciplines()) {
                boolean matchFound = false;
                for (Discipline newDiscipline : facility.getDisciplines()) {
                    if (existingDiscipline.getByFacility().getId() == newDiscipline.getByFacility().getId() &&
                            existingDiscipline.getName().equals(newDiscipline.getName())) {
                        matchFound = true;
                        break;
                    }
                }
                if (!matchFound) {
                    disciplinesToRemove.add(existingDiscipline);
                }
            }
            disciplineService.deleteAllByIds(disciplinesToRemove);
        }

        if (facilityDTO.getWorkDays() != null) {
            Set<WorkDay> newWorkDays = new HashSet<>();
            for (WorkDayDTO wdDTO : facilityDTO.getWorkDays()) {
                WorkDay workDay = mapToWorkDay(wdDTO, facility);
                newWorkDays.add(workDay);
            }
            facility.setWorkDays(newWorkDays);
        }

        if (existingFacilityOptional.isPresent()) {
            Facility existingFacility = existingFacilityOptional.get();
            Set<WorkDay> workDaysToRemove = new HashSet<>();
            for (WorkDay existingWorkDay : existingFacility.getWorkDays()) {
                boolean matchFound = false;
                for (WorkDay newWorkDay : facility.getWorkDays()) {
                    if (existingWorkDay.getFacility().getId() == newWorkDay.getFacility().getId() &&
                            existingWorkDay.getId() == newWorkDay.getId()) {
                        matchFound = true;
                        break;
                    }
                }
                if (!matchFound) {
                    workDaysToRemove.add(existingWorkDay);
                }
            }
            workDayService.deleteAllById(workDaysToRemove);
        }

        return this.facilityRepository.save(facility);
    }


    @Override
    public FacilityDTO save(Facility facility) {
        this.facilityRepository.save(facility);
        return null;
    }

//    @Override
//    public void deleteById(Long id) {
//        facilityRepository.deleteById(id);
//    }
@Override
@Transactional
public void deleteById(Long id) {
    // Your delete logic here
    disciplineService.deleteByFacilityId(id);
    deleteWorkDaysByFacilityId(id);
    facilityRepository.deleteById(id);
}

    @Override
    @Transactional
    public void deleteWorkDaysByFacilityId(Long facilityId) {
        // This is where you perform the delete operation
        // Example:
        workDayRepository.deleteByFacilityId(facilityId);
    }


    @Override
    public Optional<Facility> findByAddress(String adress) {
        return facilityRepository.findFirstByAddress(adress);
    }

    @Override
    public List<FacilityDTO> findAll() {
        List<FacilityDTO> facilityDTOS = new ArrayList<>();
        List<Facility> facilities = facilityRepository.findAll();
        for(Facility facility : facilities){
            FacilityDTO facilityDTO = new FacilityDTO(facility);
            if(facilityDTO.getReviews() != null){
                Double totalRating = calculateRating(facilityDTO);
                facilityDTO.setTotalRating(totalRating);
                if(facility.getTotalRating() == null || facilityDTO.getTotalRating() != facility.getTotalRating()){
                    facility.setTotalRating(totalRating);
                    facilityRepository.save((facility));
                }
            }
            facilityDTOS.add(facilityDTO);
        }
        return facilityDTOS;
    }

    public Double calculateRating(FacilityDTO f){
        Double rating = 0.0;
        int counter = 0;
        Set<ReviewDTO> reviews = f.getReviews();
        for(ReviewDTO r : reviews){
            Optional<Rate> rate = rateService.findById(r.getRate().getId());
            if (rate.isPresent()) {
                Rate rateC = rate.get();
                rating += (double)(rateC.getEquipment() + rateC.getHygene() + rateC.getStaff() + rateC.getSpace());
                counter++;
            }
        }
        if(counter != 0){
            counter *= 4;
            rating /= counter;
        }
        return rating;
    }

    private Discipline mapToDiscipline(DisciplineDTO disciplineDTO, Facility facility) {
        Discipline discipline = new Discipline();
        discipline.setId(disciplineDTO.getId());
        discipline.setName(disciplineDTO.getName());
        discipline.setByFacility(facility);
        return discipline;
    }

    private WorkDay mapToWorkDay(WorkDayDTO workDayDTO, Facility facility) {
        WorkDay workDay = new WorkDay();
        workDay.setId(workDayDTO.getId());
        workDay.setValidFrom(workDayDTO.getValidFrom());
        workDay.setDayOfWeek(workDayDTO.getDay());
        workDay.setFrom(workDayDTO.getFromTime());
        workDay.setUntil(workDayDTO.getUntilTime());
        workDay.setFacility(facility);
        return workDay;
    }

    private Image mapToImage(ImageDTO imageDTO, Facility facility) {
        Image image = new Image();
        image.setId(imageDTO.getId());
        image.setPath(imageDTO.getPath());
        image.setBelongsToFacility(facility);
        return image;
    }
}