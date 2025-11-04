package fitpass.fitpass.repository;

import fitpass.fitpass.model.entity.Manages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagesRepository extends JpaRepository<Manages, Long> {
    void delete(Manages manages);

    Manages findByUserId(Long id);
    Manages findByUserIdAndFacilityId(Long userId, Long facilityId);
    List<Manages> findAllByFacilityId(Long id);
}
