package fitpass.fitpass.repository;

import fitpass.fitpass.model.entity.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
        @Modifying
        @Query("DELETE FROM WorkDay wd WHERE wd.facility.id = :facilityId")
        void deleteByFacilityId(@Param("facilityId") Long facilityId);
}
