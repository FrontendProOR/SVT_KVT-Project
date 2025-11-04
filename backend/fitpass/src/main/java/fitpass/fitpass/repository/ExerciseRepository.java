package fitpass.fitpass.repository;

import fitpass.fitpass.model.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query(value = "SELECT * FROM exercises WHERE user_id = :user_id", nativeQuery = true)
    List<Exercise> myExercises(@Param("user_id") Long userId);
}
