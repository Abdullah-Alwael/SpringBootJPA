package com.spring.boot.springbootjpa.Repository;

import com.spring.boot.springbootjpa.Model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
    // empty for now
}
