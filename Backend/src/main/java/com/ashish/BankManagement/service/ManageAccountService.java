package com.ashish.BankManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ashish.BankManagement.enums.AccountStatus;
import com.ashish.BankManagement.exception.AccountNotFoundException;
import com.ashish.BankManagement.exception.ImproperAccountDetailsException;
import com.ashish.BankManagement.model.BankAccount;
import com.ashish.BankManagement.repository.BankRepo;

@Service
public class ManageAccountService {

    @Autowired
    BankRepo bankRepo;

    public String createBankAccount(BankAccount bankAccount){
        if (bankAccount == null){
            throw new ImproperAccountDetailsException("Entered Account Details are not proper!");
        }
        if (bankAccount.getTransactionHistory() == null){
            bankAccount.setTransactionHistory(new ArrayList<>());
        }

        if (bankAccount.getCurrentBalance() < 1000){
            throw new ImproperAccountDetailsException("Starting Balance Must be equal or greater than 1000");
        }
        bankAccount.setAccountStatus(AccountStatus.ACTIVE);
        long calculatedAccountNumber = (long) (Math.random() * 900000000L) + 100000000L; 
        bankAccount.setAccountNumber(calculatedAccountNumber);
        this.bankRepo.save(bankAccount);

        return "Bank Account Created Successfully";
    }

    public List<BankAccount> getAllBankAccounts() {   
        List<BankAccount> allAccount = this.bankRepo.findAll();
        return allAccount;
    }

    public String deleteAccountByID(Integer accountId) {
        if (accountId == null){
            throw new IllegalArgumentException("Account ID cannot be NUll");
        }
        Optional<BankAccount> bankAccount = this.bankRepo.findById(accountId);
        if (bankAccount.isEmpty()){
            throw new AccountNotFoundException("Bank Account Not Found!");
        }
        this.bankRepo.deleteById(accountId);
        return "Account with ID-" + accountId + " Succesfully Deleted!";

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
