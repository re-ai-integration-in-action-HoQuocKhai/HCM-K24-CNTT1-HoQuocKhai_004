package vn.rikkei.exam.equipmentloan.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.rikkei.exam.equipmentloan.model.ReservationRequest;
import vn.rikkei.exam.equipmentloan.model.ResourceType;

import java.util.Optional;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, String> {
    Optional<ReservationRequest> findByResourceType(ResourceType resourceType);
}
