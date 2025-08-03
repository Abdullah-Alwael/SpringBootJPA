package com.spring.boot.springbootjpa.Repository;

import com.spring.boot.springbootjpa.Model.MerchantStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantStockRepository extends JpaRepository<MerchantStock, Integer> {
    // empty for now
}
