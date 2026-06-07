package com.ashish.BankManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ashish.BankManagement.exception.AccountNotFoundException;
import com.ashish.BankManagement.exception.ImproperAccountDetailsException;
import com.ashish.BankManagement.model.BankAccount;
import com.ashish.BankManagement.repository.BankRepo;

@Service
public class ManageAccountService {

    @Autowired
    BankRepo bankRepo;

    public ResponseEntity<String> createBankAccount(BankAccount bankAccount){   // further improvements like user will only send the creation DTO , which include ownernae , balance and the accounttype , even the accountNumber wil be generated on the backend
        if (bankAccount == null){
            throw new ImproperAccountDetailsException("Entered Account Details are not proper!");
        }
        if (bankAccount.getCurrentBalance() < 1000){
            throw new ImproperAccountDetailsException("Starting Balance Must be equal or greater than 1000");
        }
        if (bankAccount.getTransactionHistory() == null){
            bankAccount.setTransactionHistory(new ArrayList<>());
        }
        this.bankRepo.save(bankAccount);
        return new ResponseEntity<>("Bank Account Created Successfully" , HttpStatus.CREATED);
    }

    public ResponseEntity<List<BankAccount>> getAllBankAccounts() {   
        List<BankAccount> allAccount = this.bankRepo.findAll();
        return new ResponseEntity<>(allAccount, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteAccountByID(Integer accountId) {
        if (accountId == null){
            throw new IllegalArgumentException("Account ID cannot be NUll");
        }
        Optional<BankAccount> bankAccount = this.bankRepo.findById(accountId);
        if (bankAccount.isEmpty()){
            throw new AccountNotFoundException("Bank Account Not Found!");
        }
        this.bankRepo.deleteById(accountId);
        return new ResponseEntity<>("Account with ID-" + accountId + " Succesfully Deleted!" , HttpStatus.OK);

        // try{
        //     this.bankRepo.deleteById(accountId);
        //     return new ResponseEntity<>("Account with ID-" + accountId + " Succesfully Deleted!" , HttpStatus.OK);
        // }
        // catch(Exception e){
        //     e.printStackTrace();
        //     return new ResponseEntity<>("ID not in the Database!" , HttpStatus.BAD_REQUEST);
        // }
        
    }

    public ResponseEntity<BankAccount> getAccountByID(Integer accountId) {
        if (accountId == null){
            throw new IllegalArgumentException("Account ID cannot be NUll");
        }
        Optional<BankAccount> account = this.bankRepo.findById(accountId);
        if (account.isEmpty()){
            throw new AccountNotFoundException("Bank Account Not Found!");
        }
        BankAccount bankAccount = account.get();
        return new ResponseEntity<>(bankAccount , HttpStatus.OK);
        // }
        // catch(Exception e){
        //     e.printStackTrace();
        //     return new ResponseEntity<>(bankAccount , HttpStatus.BAD_REQUEST);
        // }
        
    }

    public ResponseEntity<String> updateBankAccount(BankAccount bankAccount) {
        this.bankRepo.save(bankAccount);
        return new ResponseEntity<>("Bank Account with " + bankAccount.getAccountNumber() + " updated Succesfully" , HttpStatus.OK);
    }
}
