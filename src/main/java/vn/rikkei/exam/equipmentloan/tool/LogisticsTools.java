package vn.rikkei.exam.equipmentloan.tool;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import vn.rikkei.exam.equipmentloan.entity.Delivery;
import vn.rikkei.exam.equipmentloan.entity.Incident;
import vn.rikkei.exam.equipmentloan.model.ReservationRequest;
import vn.rikkei.exam.equipmentloan.model.ResourceInventory;
import vn.rikkei.exam.equipmentloan.model.ResourceType;
import vn.rikkei.exam.equipmentloan.repository.DeliveryRepository;
import vn.rikkei.exam.equipmentloan.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import vn.rikkei.exam.equipmentloan.repository.ReservationRequestRepository;
import vn.rikkei.exam.equipmentloan.repository.ResourceInventoryRepository;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogisticsTools {

    private final ReservationRequestRepository reservationRequestRepository;

    @Tool(description = "Tra cứu thông tin và trạng thái đơn hàng theo mã vận đơn.")
    public String getEquipmentAvailability(
            @ToolParam(description = "thiet bi CNTT còn hạng, kiểm tra startDate < endDate") ResourceType resourceType, LocalDate startDate, LocalDate endDate
    ) {

        Optional<ReservationRequest> opt = reservationRequestRepository.findByResourceType(resourceType);
        if (opt.isEmpty()) {
            return "không tìm thấy hàng có mã: " + resourceType;
        }

        ReservationRequest d = opt.get();

        return String.format("Loại %s | trạng thái: %s | Ngày hết hạn: %s | Ngày bắt đầu: %s ",
                d.getResourceType(), d.getStatus(), d.getEndDate(), d.getStartDate());
    }


    @Tool(description = "Tạo phiếu sự cố mới khi khách hàng phản ánh vấn đề với đơn hàng.")
    public String createEquipmentLoanRequest(
            @ToolParam(description = "id user") String userId,
            @ToolParam(description = "resourceCode | displayName | maxParticipants | active") ResourceType resourceType,
            @ToolParam(description = "Ngày bắt đầu") LocalDate startDate,
            @ToolParam(description = "Ngày kết thúc") LocalDate endDate,
            @ToolParam(description = "Mô tả chi tiết sự cố") String participantCount
    ) {


        return "";
    }


}
