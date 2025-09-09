package wgu.edu.BrinaBright.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wgu.edu.BrinaBright.Entities.SewerRate;

import java.time.LocalDate;
import java.util.List;

public interface SewerRateRepository extends JpaRepository<SewerRate, Long> { @Query("""
    SELECT sr FROM SewerRate sr
    WHERE sr.municipality.id = :muniId
      AND sr.effectiveStart <= :date
      AND (sr.effectiveEnd IS NULL OR sr.effectiveEnd >= :date)
    ORDER BY sr.effectiveStart DESC
  """)
List<SewerRate> findActiveForDate(@Param("muniId") Long muniId, @Param("date") LocalDate date);
}