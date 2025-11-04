package fitpass.fitpass.repository;

import fitpass.fitpass.model.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ratings (equipment, staff, hygene, space, facility_id) VALUES (:equipment, :staff, :hygene, :space, :facility_id)", nativeQuery = true)
    int insert(@Param("equipment") int equipment, @Param("staff") int staff, @Param("hygene") int hygene, @Param("space") int space, @Param("facility_id") int facility_id);

    @Query(value ="SELECT * FROM ratings WHERE facility_id = :id", nativeQuery = true)
    List<Rate> facilityRatings(@Param("id") int id);
}
