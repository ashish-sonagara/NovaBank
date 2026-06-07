package com.ashish.BankManagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.BankManagement.model.BankAccount;
import com.ashish.BankManagement.service.ManageAccountService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("manage-accounts")
public class manageAccountController {

    @Autowired
    ManageAccountService manageAccountService;

    @GetMapping("get-accounts")
    public ResponseEntity<List<BankAccount>> getMethodName() {
        return this.manageAccountService.getAllBankAccounts();
    }

    @GetMapping("get-account-by-id/{accoundID}")
    public ResponseEntity<BankAccount> getAccountByID(@PathVariable Integer accoundID) {
        return this.manageAccountService.getAccountByID(accoundID);
    }
    
    @PutMapping("updateAccount")
    public ResponseEntity<String> updateAccount(@RequestBody BankAccount bankAccount) {
        return this.manageAccountService.updateBankAccount(bankAccount);
    }

    @PostMapping("create-account")
    public ResponseEntity<String> postMethodName(@RequestBody BankAccount bankAccount) {
        return this.manageAccountService.createBankAccount(bankAccount);
    }
    
    @DeleteMapping("delete-account/{accountId}")
    public ResponseEntity<String> deleteAccountByID(@PathVariable Integer accountId){
        return this.manageAccountService.deleteAccountByID(accountId);
    }
}
