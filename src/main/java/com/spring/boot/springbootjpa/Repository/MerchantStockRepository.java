package com.spring.boot.springbootjpa.Repository;

import com.spring.boot.springbootjpa.Model.MerchantStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantStockRepository extends JpaRepository<MerchantStock, Integer> {
    // empty for now
}
