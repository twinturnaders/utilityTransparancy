package wgu.edu.BrinaBright.Repos;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wgu.edu.BrinaBright.DTOs.UserBillDTO;
import wgu.edu.BrinaBright.Entities.User;
import wgu.edu.BrinaBright.Entities.UserBill;

import java.util.List;
import java.util.Optional;

public interface UserBillRepository extends JpaRepository<UserBill, Long> {
    // Eagerly load the element collection to avoid LazyInitialization issues
    @EntityGraph(attributePaths = "fees")
    List<UserBill> findByUserIdOrderByBillDateDesc(Long userId);

    // (optional for single view)
    @EntityGraph(attributePaths = "fees")
    java.util.Optional<UserBill> findByIdAndUserId(Long id, Long userId);

}
    


   