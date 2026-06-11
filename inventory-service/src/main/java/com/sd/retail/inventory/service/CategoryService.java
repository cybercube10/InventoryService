package com.sd.retail.inventory.service;

import com.sd.retail.inventory.exception.CategoryNotFoundException;
import com.sd.retail.inventory.model.Category;
import com.sd.retail.inventory.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }


  public String addCategory(String categoryName){
      boolean categoryExists = categoryRepository.existsByCategoryNameIgnoringCase(categoryName);
      if(categoryExists){
          throw new CategoryNotFoundException("Category already exists");
      }
        Category category = new Category();
        category.setCategoryName(categoryName.toLowerCase());
          categoryRepository.save(category);
          return "category added successfully";

  }
    public Page<Category> getAllCategories(int page, int size) {
       if(page <= 0)page = 0;
       if(size <= 0)size = 10;
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable);
    }

    public Page<Category> searchCategoryByName(String categoryName,int page,int size) {
        if(page <= 0)page = 0;
        if(size <= 0)size = 10;
        Pageable pageable = PageRequest.of(page, size);
        return  categoryRepository.findByCategoryNameContainingIgnoreCase( categoryName,  pageable);
  }
}
