package fitpass.fitpass.repository;

import fitpass.fitpass.model.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Optional<Facility> findFirstByAddress(String address);

    @Modifying
    @Transactional
    @Query(value = "UPDATE facilities SET total_rating = :total_rating WHERE id = :id", nativeQuery = true)
    void alterRating(@Param("total_rating") double total_rating, @Param("id") int id);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM facilities WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);

}
