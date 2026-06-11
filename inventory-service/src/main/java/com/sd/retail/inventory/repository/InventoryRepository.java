package com.sd.retail.inventory.repository;

import com.sd.retail.inventory.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
    Optional<Inventory> findByTenantIdAndBatchID(Long tenantId, UUID  BatchID);
}
