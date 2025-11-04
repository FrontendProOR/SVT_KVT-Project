package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.dto.RateDTO;
import fitpass.fitpass.model.entity.Rate;
import fitpass.fitpass.repository.RateRepository;
import fitpass.fitpass.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateServiceImpl implements RateService {

    @Autowired
    RateRepository rateRepository;

    @Override
    public Rate create(RateDTO rateDTO) {
        return null;
//        Rate rate = new Rate();
//        rate.setEquipment(rateDTO.getEquipment());
//        rate.setHygene(rateDTO.getHygene());
//        rate.setStaff(rateDTO.getStaff());
//        rate.setSpace(rateDTO.getSpace());
////        rate.setFacility(Double.valueOf(rateDTO.getFacilityId()));
////        rate.setFacility(Double.valueOf(rateDTO.getFacility()));
//        return rateRepository.save(rate);
    }

    @Override
    public Optional<Rate> findById(Long id) {
        return rateRepository.findById(id);
    }
}
