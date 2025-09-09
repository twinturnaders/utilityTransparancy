package wgu.edu.BrinaBright.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wgu.edu.BrinaBright.Entities.RateVariance;


import java.time.LocalDate;
import java.util.List;

public interface RateVarianceRepository extends JpaRepository<RateVariance, Long> {

}