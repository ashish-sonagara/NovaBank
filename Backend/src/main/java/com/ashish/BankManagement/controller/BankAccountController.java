package com.ashish.BankManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.BankManagement.dto.response.ResponseDTO;
import com.ashish.BankManagement.dto.response.TransactionDTO;
import com.ashish.BankManagement.service.BankAccountService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("bank-service")
@CrossOrigin(origins = "http://localhost:4200")

public class BankAccountController {
    
    @Autowired
    BankAccountService bankAccountService;

    @PostMapping("deposit")
    public ResponseEntity<ResponseDTO> depositAmount(@RequestParam Long accountNumber, @RequestParam double amount){  // int doesnt allow null, but Integer could be null

        String result = this.bankAccountService.depositAmount(accountNumber, amount);

        ResponseDTO response = new ResponseDTO();
        response.setMessage("Deposited SuccessFully");
        response.setSuccess(1);
        response.setServiceResult(result);

        return ResponseEntity.ok(response);
    }

    @PostMapping("withdraw")
    public ResponseEntity<ResponseDTO> withDrawAmount(@RequestParam Long accountNumber, @RequestParam double amount) {

        String result = this.bankAccountService.withdrawAmount(accountNumber , amount);

        ResponseDTO response = new ResponseDTO();
        response.setMessage("Deposited SuccessFully");
        response.setSuccess(1);
        response.setServiceResult(result);

        return ResponseEntity.ok(response);
    }
    
    @PostMapping("transfer")
    public ResponseEntity<ResponseDTO> transferMoney(@RequestParam Long accountNumber1, @RequestParam Long accountNumber2,@RequestParam double amount) {
        String result =  this.bankAccountService.transferMoney(accountNumber1, accountNumber2 , amount);

        ResponseDTO response = new ResponseDTO();
        response.setMessage("Deposited SuccessFully");
        response.setSuccess(1);
        response.setServiceResult(result);

        return ResponseEntity.ok(response);
    }

    @GetMapping("transactions")
    public ResponseEntity<ResponseDTO> fetchAllTransactions() { // params could be updated later on , not now. 

        List<TransactionDTO> transactionDTOList = this.bankAccountService.fetchAllTransaction();

        ResponseDTO response = new ResponseDTO();
        response.setMessage("Deposited SuccessFully");
        response.setSuccess(1);
        response.setServiceResult(transactionDTOList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("last-n-transactions")
    public ResponseEntity<ResponseDTO> fetchLastNTransaction(@RequestParam Integer n) { // params could be updated later on , not now. 

        List<TransactionDTO> transactionDTOList = this.bankAccountService.fetchLastNTransaction(n);

        ResponseDTO response = new ResponseDTO();
        response.setMessage("Deposited SuccessFully");
        response.setSuccess(1);
        response.setServiceResult(transactionDTOList);

        return ResponseEntity.ok(response);
    }
    
}
