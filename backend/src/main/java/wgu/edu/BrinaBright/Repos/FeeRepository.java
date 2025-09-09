package wgu.edu.BrinaBright.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import wgu.edu.BrinaBright.Entities.Fee;

import java.util.List;

public interface FeeRepository extends JpaRepository<Fee, Long> {

}