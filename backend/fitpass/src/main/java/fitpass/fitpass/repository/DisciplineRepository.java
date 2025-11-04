package fitpass.fitpass.repository;

import fitpass.fitpass.model.entity.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    void deleteById(Long id);
    Discipline findByName(String name);
    @Modifying
    @Transactional
    @Query("DELETE FROM Discipline d WHERE d.byFacility.id = :facilityId")
    void deleteByFacilityId(Long facilityId);
}
