package com.ashish.BankManagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.BankManagement.dto.response.ResponseDTO;
import com.ashish.BankManagement.model.BankAccount;
import com.ashish.BankManagement.service.ManageAccountService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("manage-accounts")
@CrossOrigin(origins = "http://localhost:4200")
public class manageAccountController {

    @Autowired
    ManageAccountService manageAccountService;

    @GetMapping("get-accounts")
    public ResponseEntity<ResponseDTO> getAllBankAccounts() {
        List<BankAccount> bankAccounts = this.manageAccountService.getAllBankAccounts();

        ResponseDTO response = new ResponseDTO();
        response.setMessage("Accounts Fetched SuccessFully");
        response.setSuccess(1);
        response.setServiceResult(bankAccounts);

        return ResponseEntity.ok(response);
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
    public ResponseDTO createBankAccount(@RequestBody BankAccount bankAccount) {
        String message = this.manageAccountService.createBankAccount(bankAccount);
        ResponseDTO response = new ResponseDTO();
        response.setMessage(message);
        response.setSuccess(1);
        response.setServiceResult(message);

        return response;
    }
    
    @DeleteMapping("delete-account/{accountId}")
    public ResponseEntity<ResponseDTO> deleteAccountByID(@PathVariable Integer accountId) {
        String resultMessage = this.manageAccountService.deleteAccountByID(accountId);

        ResponseDTO response = new ResponseDTO();
        response.setMessage(resultMessage);
        response.setSuccess(1);
        response.setServiceResult(resultMessage);

        return ResponseEntity.ok(response);
    }
}
