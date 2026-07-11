package com.ashish.BankManagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ashish.BankManagement.enums.TransactionStatus;
import com.ashish.BankManagement.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private double transactionAmount;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="bank_account_id")
    @JsonIgnore
    private BankAccount bankAccount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

}
