package com.jaringochi.service;

import com.jaringochi.domain.Category;
import com.jaringochi.repository.CategoryRepository;
import java.util.List;

public class CategoryService {
    private CategoryRepository categoryRepository = new CategoryRepository();

    public List<Category> getCategories(long userId) {
        return categoryRepository.findByUserId(userId);
    }
    
    public List<Category> getCategoriesByType(long userId, String type) {
        return categoryRepository.findByUserIdAndType(userId, type);
    }

    public void addCategory(Category cat) {
        categoryRepository.save(cat);
    }

    public void deleteCategory(long id) {
        categoryRepository.delete(id);
    }
}
