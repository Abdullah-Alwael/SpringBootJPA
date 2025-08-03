package com.spring.boot.springbootjpa.Repository;

import com.spring.boot.springbootjpa.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // empty for now
}
