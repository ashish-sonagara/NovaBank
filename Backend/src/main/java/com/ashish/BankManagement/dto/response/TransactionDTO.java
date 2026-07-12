package com.ashish.BankManagement.dto.response;

import java.time.LocalDateTime;

import com.ashish.BankManagement.enums.TransactionStatus;
import com.ashish.BankManagement.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    
    private int transactionId;
    private TransactionType transactionType;
    private double transactionAmount;
    private LocalDateTime date;
    private TransactionStatus transactionStatus;
    private String accountOwner;
    private String bankName;
    private Long accountNumber;
}
