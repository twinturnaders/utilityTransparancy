
package wgu.edu.BrinaBright.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wgu.edu.BrinaBright.DTOs.CreateBillRequest;
import wgu.edu.BrinaBright.DTOs.UserBillDTO;
import wgu.edu.BrinaBright.Entities.User;
import wgu.edu.BrinaBright.Entities.UserBill;
import wgu.edu.BrinaBright.Repos.UserBillRepository;
import wgu.edu.BrinaBright.Repos.UserRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViewBillService {

    private final UserBillRepository userBillRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveBillForUser(Long userId, CreateBillRequest req) {
        User user = userRepository.getReferenceById(userId);
        UserBill bill = new UserBill();

        bill.setUser(user);
        bill.setBillDate(req.getBillDate());
        bill.setDueDate(req.getDueDate());
        bill.setPaidDate(req.getPaidDate());
        bill.setPaid(Boolean.TRUE.equals(req.getPaid()));

        bill.setWaterUsage(req.getWaterUsage());
        bill.setSewerUsage(req.getSewerUsage());
        bill.setWaterCharge(req.getWaterCharge());
        bill.setSewerCharge(req.getSewerCharge());


        bill.getFees().clear();
        if (req.getFees() != null && !req.getFees().isEmpty()) {
            req.getFees().forEach((k, v) -> bill.getFees().put(k, v != null ? v : BigDecimal.ZERO));
        }

        userBillRepository.save(bill);
    }

    @Transactional(readOnly = true)
    public List<UserBillDTO> getUserBillsForUser(Long userId) {
        List<UserBill> entities = userBillRepository.findByUserIdOrderByBillDateDesc(userId);
        if (entities == null || entities.isEmpty()) return Collections.emptyList();

        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    private UserBillDTO toDto(UserBill ub) {
        UserBillDTO dto = new UserBillDTO();
        dto.setId(ub.getId());
        dto.setBillDate(ub.getBillDate());
        dto.setDueDate(ub.getDueDate());
        dto.setPaidDate(ub.getPaidDate());
        dto.setPaid(ub.getPaid());

        dto.setWaterUsage(ub.getWaterUsage());
        dto.setSewerUsage(ub.getSewerUsage());
        dto.setWaterCharge(ub.getWaterCharge());
        dto.setSewerCharge(ub.getSewerCharge());


        dto.setFees(ub.getFees());


        return dto;
    }


}
