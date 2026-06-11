package com.sd.retail.inventory.controller;

import com.sd.retail.inventory.dto.ProductRequestDTO;
import com.sd.retail.inventory.dto.ProductResponseDTO;
import com.sd.retail.inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product/")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO){

        String response = productService.addProduct(productRequestDTO);
        return ResponseEntity.ok(response);


    }

    @GetMapping("")
    public ResponseEntity<Page<ProductResponseDTO>>  getAllProducts(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        Page<ProductResponseDTO> products = productService.getProducts(page, size);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProduct(@RequestParam String productname,@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        Page <ProductResponseDTO> products = productService.searchProductByName(productname,page,size);
        return ResponseEntity.ok(products);
    }


}
