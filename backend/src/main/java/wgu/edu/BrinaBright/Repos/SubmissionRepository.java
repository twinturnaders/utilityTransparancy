package wgu.edu.BrinaBright.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import wgu.edu.BrinaBright.Entities.CrowdSubmission;
import wgu.edu.BrinaBright.Entities.UserBill;

public interface SubmissionRepository extends JpaRepository<CrowdSubmission, Long> {
}
