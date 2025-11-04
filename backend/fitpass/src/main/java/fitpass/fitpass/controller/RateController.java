package fitpass.fitpass.controller;

import fitpass.fitpass.model.dto.RateDTO;
import fitpass.fitpass.model.entity.Rate;
import fitpass.fitpass.repository.FacilityRepository;
import fitpass.fitpass.repository.RateRepository;
import fitpass.fitpass.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/rates")
public class RateController {
    @Autowired
    RateService rateService;

    @Autowired
    RateRepository rateRepository;
    @Autowired
    private FacilityRepository facilityRepository;

    @PostMapping("/newrating")
    ResponseEntity<RateDTO> create(@RequestBody @Validated RateDTO rateDto){
        System.out.println(rateDto.getEquipment());
        System.out.println(rateDto.getHygene());
        System.out.println(rateDto.getSpace());
        System.out.println(rateDto.getFacilityId());

        rateRepository.insert(rateDto.getEquipment(), rateDto.getStaff(),rateDto.getHygene(), rateDto.getSpace(), rateDto.getFacilityId());

        List<Rate> facilityRating = rateRepository.facilityRatings(rateDto.getFacilityId());
        List<Double> meanValues = new ArrayList<>();
        for (Rate rate : facilityRating) {
            double allRates = (double) (rate.getHygene() + rate.getEquipment() + rate.getStaff() + rate.getSpace()) / 4;
            meanValues.add(allRates);
        }
        double addedValues = 0;
        for (Double meanValue : meanValues) {
            addedValues = addedValues + meanValue;
        }
        facilityRepository.alterRating(addedValues/meanValues.size(), rateDto.getFacilityId());



        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Rate> getOne(@PathVariable Long id){
        Optional<Rate> rate = rateService.findById(id);
        if(!rate.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Rate rateResponse = rate.get();
        return new ResponseEntity<>(rateResponse,HttpStatus.OK);
    }
}
