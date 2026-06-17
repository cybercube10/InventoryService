package com.sd.retail.inventory.repository;

import com.sd.retail.inventory.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByTenantIdAndSku(Long tenantId,String sku);
    Page<Product> findByTenantIdAndProductNameContainingIgnoreCase(Long tenantId,String productName, Pageable pageable);

    Page<Product> findAllByTenantId(Long tenantId, Pageable pageable);

    Optional<Product> findByProductId(Long  productId);
}
