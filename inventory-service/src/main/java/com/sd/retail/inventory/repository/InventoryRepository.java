package com.sd.retail.inventory.repository;

import com.sd.retail.inventory.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Page<Inventory> findByTenantIdAndProductProductId(
            Long userId,
            Long productId,
            Pageable pageable
    );
    Page<Inventory> findAllByTenantId(Long tenantId, Pageable pageable);
    Optional<Inventory> findByTenantIdAndBatchId(Long tenantId, UUID  BatchID);

    Inventory findByBatchId(UUID batchId);
    @Modifying
    @Query("""
UPDATE Inventory i
SET i.availableQty = i.availableQty - :qty
WHERE i.batchId = :batchId
AND i.availableQty >= :qty
""")
    int reserveStock(UUID batchId, int qty);

    List<Inventory> findInventoryByExpiryDateBetween(LocalDate startDate, LocalDate endDate);
}
