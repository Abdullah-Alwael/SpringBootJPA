package com.spring.boot.springbootjpa.Service;

import com.spring.boot.springbootjpa.Model.Category;
import com.spring.boot.springbootjpa.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Integer categoryID) {
        return categoryRepository.getById(categoryID);
    }

    public Boolean updateCategory(Integer categoryID, Category category) {
        Category oldCategory = categoryRepository.getById(categoryID);

        if (oldCategory == null) {
            return false;
        }

        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);

        return true;
    }

    public Boolean deleteCategory(Integer categoryID) {
        Category deleteCategory = categoryRepository.getById(categoryID);

        if (deleteCategory == null) {
            return false;
        }

        categoryRepository.delete(deleteCategory);
        return true;
    }

    public Boolean checkAvailableCategory(Integer categoryID) {
        Category checkCategory = categoryRepository.getById(categoryID);

        if (checkCategory == null){
            return false;
        }

        return checkCategory.getId().equals(categoryID); // always true?
    }
}
