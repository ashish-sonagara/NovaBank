package com.ashish.BankManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashish.BankManagement.model.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction , Integer> {
    
}
