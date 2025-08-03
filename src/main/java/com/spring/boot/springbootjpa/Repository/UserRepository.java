package com.spring.boot.springbootjpa.Repository;

import com.spring.boot.springbootjpa.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // empty for now
}
