package com.sd.retail.inventory.controller;

import com.sd.retail.inventory.dto.InventoryRequestDTO;
import com.sd.retail.inventory.dto.InventoryResponseDTO;
import com.sd.retail.inventory.dto.InventoryItemResponseDTO;

import com.sd.retail.inventory.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("inventory/")
public class InventoryController {
    private final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    @PostMapping("")
    public ResponseEntity<String> addProduct(@RequestBody InventoryRequestDTO inventoryRequestDTO)
    {
        String response = inventoryService.addInventory(inventoryRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<Page<InventoryResponseDTO>>  getAllInventory(@RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "10") Integer size){
        Page <InventoryResponseDTO> inventoryResponseDTOPage = inventoryService.getAllInventory(page, size);
        return ResponseEntity.ok(inventoryResponseDTOPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<InventoryResponseDTO>> searchInventoryByProduct(@RequestParam Long productId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        Page<InventoryResponseDTO>  response = inventoryService.searchInventory(productId,size, page);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<InventoryItemResponseDTO> getInventoryItemByBatchID(@RequestParam UUID BatchId){
        InventoryItemResponseDTO inventoryItemResponseDTO = inventoryService.getItemByBatchID(BatchId);
        return ResponseEntity.ok(inventoryItemResponseDTO);
    }

    @GetMapping("/available")
    public ResponseEntity<Boolean> getStockAvailability(@RequestParam UUID BatchID,@RequestParam int qty){
        return ResponseEntity.ok(inventoryService.getStockAvailability(BatchID,qty));
    }
}
