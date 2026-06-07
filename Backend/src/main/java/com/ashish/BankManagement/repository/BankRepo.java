package com.ashish.BankManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashish.BankManagement.model.BankAccount;

@Repository
public interface BankRepo extends JpaRepository<BankAccount, Integer> {   // can also be called as BankDao.
    
}
