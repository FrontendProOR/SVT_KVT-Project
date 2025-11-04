package fitpass.fitpass.service;

import fitpass.fitpass.model.dto.RateDTO;
import fitpass.fitpass.model.entity.Rate;

import java.util.Optional;

public interface RateService {
    public Rate create(RateDTO rateDTO);
    public Optional<Rate> findById(Long id);
}
