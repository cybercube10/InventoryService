package com.sd.retail.inventory.service;

import com.sd.retail.inventory.dto.InventoryItemResponseDTO;
import com.sd.retail.inventory.dto.InventoryRequestDTO;
import com.sd.retail.inventory.dto.InventoryResponseDTO;
import com.sd.retail.inventory.exception.InventoryNotFoundException;
import com.sd.retail.inventory.exception.ProductNotFoundException;
import com.sd.retail.inventory.model.Inventory;
import com.sd.retail.inventory.model.Product;
import com.sd.retail.inventory.repository.InventoryRepository;
import com.sd.retail.inventory.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InventoryService {
    Long userId = 1L;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    public InventoryService(InventoryRepository inventoryRepository,ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
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
        Inventory item = inventoryRepository.findByTenantIdAndBatchID(userId,BatchID).orElseThrow(()->new InventoryNotFoundException("item not found"));
        Product p = productRepository.findByProductID(item.getProduct().getProductId()).orElseThrow(()->new ProductNotFoundException("product doesn't exist"));
        InventoryItemResponseDTO inventoryItemResponseDTO = new InventoryItemResponseDTO();
        inventoryItemResponseDTO.setBatchId(BatchID);
        inventoryItemResponseDTO.setProductName(p.getProductName());
        inventoryItemResponseDTO.setPrice(p.getSellingPrice());
        inventoryItemResponseDTO.setAvailableQuantity(item.getAvailableQty());
        return inventoryItemResponseDTO;
    }


    public  Boolean getStockAvailability(UUID batchID, int qty) {
        Inventory inventory = inventoryRepository.findByTenantIdAndBatchID(userId,batchID).orElseThrow(() ->
                new InventoryNotFoundException(
                        "Batch not found"));;

       return inventory.getAvailableQty() >= qty;

    }
}
