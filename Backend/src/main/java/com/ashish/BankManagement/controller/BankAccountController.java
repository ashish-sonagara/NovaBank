package com.ashish.BankManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.BankManagement.service.BankAccountService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("bank-service")
public class BankAccountController {
    
    @Autowired
    BankAccountService bankAccountService;

    @PostMapping("deposit")
    public ResponseEntity<String> depositAmount(@RequestParam Integer id, @RequestParam double amount){  // int doesnt allow null, but Integer could be null
        return this.bankAccountService.depositAmount(id, amount); 
    }

    @PostMapping("withdraw")
    public ResponseEntity<String> withDrawAmount(@RequestParam Integer accountID, @RequestParam double amount) {
        return this.bankAccountService.withdrawAmount(accountID , amount);
    }
    
    @PostMapping("transfer")
    public ResponseEntity<String> transferMoney(@RequestParam Integer accountID_1, @RequestParam Integer accountID_2,@RequestParam double amount) {
        return this.bankAccountService.transferMoney(accountID_1, accountID_2 , amount);
    }
}
