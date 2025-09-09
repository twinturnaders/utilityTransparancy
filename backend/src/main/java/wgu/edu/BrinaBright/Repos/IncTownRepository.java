package wgu.edu.BrinaBright.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wgu.edu.BrinaBright.DTOs.TownOnlyDTO;
import wgu.edu.BrinaBright.Entities.IncTown;

import java.util.List;


public interface IncTownRepository extends JpaRepository<IncTown, Long> {
    @Query("select new wgu.edu.BrinaBright.DTOs.TownOnlyDTO(t.townName) " +
            "from IncTown t order by lower(t.townName) asc")

    List<TownOnlyDTO> findAllNamesAsc();

}