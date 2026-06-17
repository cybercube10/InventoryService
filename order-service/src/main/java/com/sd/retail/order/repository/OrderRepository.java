package com.sd.retail.order.repository;

import com.sd.retail.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByOrderId(Long oid);
}
