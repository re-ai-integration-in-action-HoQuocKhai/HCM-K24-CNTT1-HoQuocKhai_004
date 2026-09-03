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
    )
    }

    /**
     * Tool 3: updateDeliveryStatus (Write)
     * Cập nhật trạng thái đơn hàng trong DB
     */
    @Tool(description = "Cập nhật trạng thái đơn hàng (DAMAGED, DELAYED, IN_TRANSIT, DELIVERED).")
    public String updateDeliveryStatus(
            @ToolParam(description = "Mã vận đơn") String trackingCode,
            @ToolParam(description = "Trạng thái mới: DAMAGED | DELAYED | IN_TRANSIT | DELIVERED") String newStatus,
            @ToolParam(description = "Ghi chú bổ sung") String note
    ) {
        // TODO: Viết logic tìm delivery, cập nhật status và lưu lại
        Optional<Delivery> opt = deliveryRepository.findByTrackingCode(trackingCode.trim());

        if (opt.isEmpty()) {
            return "LỖI: Không tìm thấy đơn hàng " + trackingCode;
        }

        Delivery delivery = opt.get();
        String oldStatus = delivery.getStatus();
        delivery.setStatus(newStatus.toUpperCase());
        deliveryRepository.save(delivery);

        return String.format("THÀNH CÔNG: Đã đổi trạng thái đơn %s từ [%s] sang [%s]",
                trackingCode, oldStatus, newStatus.toUpperCase());
    }

    /**
     * Tool 4: queryDeliveries (Analytics / MCP)
     * Tra cứu danh sách đơn hàng theo bưu cục/trạng thái
     */
    @Tool(description = "Tra cứu danh sách đơn hàng theo mã bưu cục và/hoặc trạng thái.")
    public String queryDeliveries(
            @ToolParam(description = "Mã bưu cục (HN-01, SG-02, DN-03) hoặc null") String hubCode,
            @ToolParam(description = "Trạng thái đơn hàng hoặc null") String status
    ) {
        // TODO: Viết logic truy vấn đơn hàng và format bảng kết quả Markdown

        return null;
    }

    /**
     * Tool 5: generateHubReport (Analytics / MCP)
     * Báo cáo hiệu suất bưu cục
     */
    @Tool(description = "Tạo báo cáo tổng hợp hiệu suất vận hành của một bưu cục.")
    public String generateHubReport(
            @ToolParam(description = "Mã bưu cục: HN-01, SG-02, DN-03") String hubCode
    ) {
        // TODO: Viết logic thống kê tỉ lệ giao hàng, số sự cố và trả về báo cáo Markdown
        return null;
    }
}
