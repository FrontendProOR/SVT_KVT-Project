package fitpass.fitpass.service;

import fitpass.fitpass.model.dto.ManagesDTO;
import fitpass.fitpass.model.entity.Manages;

import java.util.List;

public interface ManagesService {
    Manages create(ManagesDTO manages);
    void delete(ManagesDTO managesDTO);
    Manages findByUserId(Long id);
    List<Manages> findAllByFacilityId(Long id);
    Manages findByUserIdAndFacilityId(Long userId, Long facilityId);

}
