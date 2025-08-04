package com.spring.boot.springbootjpa.Controller;

import com.spring.boot.springbootjpa.Api.ApiResponse;
import com.spring.boot.springbootjpa.Model.Category;
import com.spring.boot.springbootjpa.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category/")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService; // no need to use the new word, Spring handles it in the container

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("category was added Successfully"));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategories());
    }

    @PutMapping("/update/{categoryID}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer categoryID,
                                           @Valid @RequestBody Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (categoryService.updateCategory(categoryID, category)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("category updated Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error, not found"));
        }
    }

    @DeleteMapping("/delete/{categoryID}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryID) {
        if (categoryService.deleteCategory(categoryID)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("category deleted Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error, not found"));
        }
    }
}
