package com.ashish.BankManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashish.BankManagement.model.BankAccount;
import java.util.List;


@Repository
public interface BankRepo extends JpaRepository<BankAccount, Integer> {   // can also be called as BankDao.
    
    Optional<BankAccount> findByAccountNumber(Long accountNumber);      

}
