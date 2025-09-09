package wgu.edu.BrinaBright.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wgu.edu.BrinaBright.Entities.WaterRate;

import java.time.LocalDate;
import java.util.List;

public interface WaterRateRepository extends JpaRepository<WaterRate, Long> {
    @Query("""
    SELECT wr FROM WaterRate wr
    WHERE wr.municipality.id = :muniId
      AND wr.effectiveStart <= :date
      AND (wr.effectiveEnd IS NULL OR wr.effectiveEnd >= :date)
    ORDER BY wr.effectiveStart DESC
  """)
    List<WaterRate> findActiveForDate(@Param("muniId") Long muniId, @Param("date") LocalDate date);
}
