package com.sd.retail.inventory.controller;

import com.sd.retail.inventory.model.Category;
import com.sd.retail.inventory.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category/")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("")
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        String response = categoryService.addCategory(category.getCategoryName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("")
    public ResponseEntity<Page<Category>> getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page <Category> categories = categoryService.getAllCategories(page,size);
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Category>> getCategoryById(@RequestParam String categoryName,@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        Page<Category> categories=  categoryService.searchCategoryByName(categoryName,page,size);
        return ResponseEntity.ok().body(categories);
    }


}
