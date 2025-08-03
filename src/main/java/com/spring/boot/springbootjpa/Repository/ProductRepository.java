package com.spring.boot.springbootjpa.Repository;

import com.spring.boot.springbootjpa.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // empty for now
}
