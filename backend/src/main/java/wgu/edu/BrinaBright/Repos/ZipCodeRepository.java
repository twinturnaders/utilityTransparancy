package wgu.edu.BrinaBright.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import wgu.edu.BrinaBright.Entities.ZipCode;

import java.util.Optional;

public interface ZipCodeRepository extends JpaRepository<ZipCode, Long> {
    Optional<ZipCode> findByZipCode(String zipCode);
}