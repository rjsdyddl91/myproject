package com.example.myproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myproject.entity.Account;

public interface AccountRepository 
        extends JpaRepository<Account, Long>{
    Optional<Account> findByUsername(String username);
            
}
