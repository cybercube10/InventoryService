package com.sd.retail.inventory.service;

import com.sd.retail.commons.dto.OrderItemDTO;
import com.sd.retail.commons.event.InventoryReserveEvent;
import com.sd.retail.commons.event.OrderEvent;
import com.sd.retail.inventory.dto.InventoryItemResponseDTO;
import com.sd.retail.inventory.dto.InventoryRequestDTO;
import com.sd.retail.inventory.dto.InventoryResponseDTO;
import com.sd.retail.inventory.exception.InventoryNotFoundException;
import com.sd.retail.inventory.exception.ProductNotFoundException;
import com.sd.retail.inventory.message.InventoryReserveProducer;
import com.sd.retail.inventory.model.Inventory;
import com.sd.retail.inventory.model.Product;
import com.sd.retail.inventory.repository.InventoryRepository;
import com.sd.retail.inventory.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InventoryService {
    Long userId = 1L;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final InventoryReserveProducer inventoryReserveProducer;
    public InventoryService(InventoryRepository inventoryRepository,ProductRepository productRepository,InventoryReserveProducer inventoryReserveProducer) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.inventoryReserveProducer = inventoryReserveProducer;
    }

    public String addInventory(InventoryRequestDTO inventoryRequestDTO) {
        Product product = productRepository.findById(inventoryRequestDTO.getProductId()).orElseThrow(
                ()->  new ProductNotFoundException("product does'nt exist")
        );

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setTenantId(userId);
        inventory.setTotalQty(inventoryRequestDTO.getTotalQty());
        inventory.setAvailableQty(inventoryRequestDTO.getTotalQty());
        inventory.setExpiryDate(inventoryRequestDTO.getExpiryDate());
        inventory.setCreatedDate(LocalDateTime.now());

        Inventory newInventory = inventoryRepository.save(inventory);
        return "Inventory added successfully. Batch ID: " + newInventory.getBatchId();
    }

    public Page<InventoryResponseDTO> getAllInventory(int page, int size) {
        if(page <= 0)page = 0;
        if(size <= 0)size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<Inventory> inventoryList = inventoryRepository.findAllByTenantId(userId,pageable);
        return inventoryList.map(inventory -> InventoryResponseDTO.builder()
                .batchId(inventory.getBatchId())
                .productId(inventory.getProduct().getProductId())
                .productName(inventory.getProduct().getProductName())
                .sku(inventory.getProduct().getSku())
                .totalQty(inventory.getTotalQty())
                .availableQty(inventory.getAvailableQty())
                .expiryDate(inventory.getExpiryDate())
                .createdDate(inventory.getCreatedDate())
                .build() );
    }

    public Page<InventoryResponseDTO> searchInventory(Long productId,int size, int page) {
        if(page < 0)page = 0;
        if(size <= 0)size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<Inventory> inventoryList = inventoryRepository.findByTenantIdAndProductProductId(userId,productId,pageable);

        return inventoryList.map(inventory ->
                InventoryResponseDTO.builder()
                        .batchId(inventory.getBatchId())
                        .productId(inventory.getProduct().getProductId())
                        .productName(inventory.getProduct().getProductName())
                        .sku(inventory.getProduct().getSku())
                        .totalQty(inventory.getTotalQty())
                        .availableQty(inventory.getAvailableQty())
                        .expiryDate(inventory.getExpiryDate())
                        .createdDate(inventory.getCreatedDate())
                        .build()
        );
    }


    public InventoryItemResponseDTO getItemByBatchID (UUID BatchID){
        Inventory item = inventoryRepository.findByTenantIdAndBatchId(userId,BatchID).orElseThrow(()->new InventoryNotFoundException("item not found"));
        Product p = productRepository.findByProductId(item.getProduct().getProductId()).orElseThrow(()->new ProductNotFoundException("product doesn't exist"));
        InventoryItemResponseDTO inventoryItemResponseDTO = new InventoryItemResponseDTO();
        inventoryItemResponseDTO.setBatchId(BatchID);
        inventoryItemResponseDTO.setProductName(p.getProductName());
        inventoryItemResponseDTO.setPrice(p.getSellingPrice());
        inventoryItemResponseDTO.setAvailableQuantity(item.getAvailableQty());
        return inventoryItemResponseDTO;
    }


    public  Boolean getStockAvailability(UUID batchID, int qty) {
        Inventory inventory = inventoryRepository.findByTenantIdAndBatchId(userId,batchID).orElseThrow(() ->
                new InventoryNotFoundException(
                        "Batch not found"));;

       return inventory.getAvailableQty() >= qty;

    }

    @Transactional
    public void handleBooking(OrderEvent orderEvent) {
        boolean failed = false;
        Long oid = orderEvent.getOrderRequestDTO().getOrderId();
        UUID batchId = null;
       for (OrderItemDTO item : orderEvent.getOrderRequestDTO().getOrderItems() ){
           int updated = inventoryRepository.reserveStock(item.getBatchId(), item.getQuantity());
           if(updated == 0){
               failed = true;
           }

       }
       if(failed){
           TransactionAspectSupport.currentTransactionStatus()
                   .setRollbackOnly();
           inventoryReserveProducer.publishInventoryReserveEvent(new InventoryReserveEvent(oid,false,"insuffcicient stock for batchID "+batchId));

           return;
       }
        inventoryReserveProducer.publishInventoryReserveEvent(new InventoryReserveEvent(oid,true,"successfully reserved inventory"));

    }


}

