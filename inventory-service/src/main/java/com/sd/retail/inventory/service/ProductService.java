package com.sd.retail.inventory.service;

import com.sd.retail.inventory.dto.ProductRequestDTO;
import com.sd.retail.inventory.dto.ProductResponseDTO;
import com.sd.retail.inventory.exception.CategoryNotFoundException;
import com.sd.retail.inventory.exception.ProductAlreadyExistsException;
import com.sd.retail.inventory.model.Category;
import com.sd.retail.inventory.model.Product;
import com.sd.retail.inventory.repository.CategoryRepository;
import com.sd.retail.inventory.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    Long tenantId = 1L;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    public String addProduct( ProductRequestDTO productRequestDTO) {
        Long tenantId = 1L;
        String sku = productRequestDTO.getSku();
        boolean productExists = productRepository.existsByTenantIdAndSku(tenantId,sku);
        if(productExists){
            throw new ProductAlreadyExistsException("Product Already exists");
        }
        Product product = new Product();
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category doesn't exist"));
        product.setSku(sku);
        product.setProductName(productRequestDTO.getProductName());
        product.setCategory(category);
        product.setDescription(productRequestDTO.getDescription());
        product.setCostPrice(productRequestDTO.getCostPrice());
        product.setSellingPrice(productRequestDTO.getSellingPrice());
        product.setTenantId(tenantId);
        Product saved = productRepository.save(product);
        return "Product created with id: " + saved.getProductId();
    }

    public Page<ProductResponseDTO> getProducts(int page, int size) {
        if(page <= 0)page = 0;
        if(size <= 0)size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByTenantId(tenantId,pageable);
        return products.map(product -> ProductResponseDTO.builder()
                .categoryId(product.getCategory().getCategoryId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .costPrice(product.getCostPrice())
                .sellingPrice(product.getSellingPrice())
                .sku(product.getSku())
                .build()
        );
    }



    public Page<ProductResponseDTO> searchProductByName(String productname,int page,int size) {
        if(productname == null || productname.isEmpty()){throw new IllegalArgumentException("Product name cannot be empty");}

        if(page <= 0)page = 0;
        if(size <= 0)size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> products =  productRepository.findByTenantIdAndProductNameContainingIgnoreCase( tenantId,productname,  pageable);

        return products.map(product -> ProductResponseDTO.builder()
                .categoryId(product.getCategory().getCategoryId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .costPrice(product.getCostPrice())
                .sellingPrice(product.getSellingPrice())
                .sku(product.getSku())
                .build()
        );

    }


}
