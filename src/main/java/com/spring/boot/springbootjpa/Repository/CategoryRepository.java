package com.spring.boot.springbootjpa.Repository;

import com.spring.boot.springbootjpa.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // empty for now
}
