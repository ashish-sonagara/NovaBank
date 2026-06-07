package com.ashish.BankManagement.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ashish.BankManagement.enums.AccountType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Component   cause no need to create the object as the program start , user will create it , also , @entity is enough 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String accountOwner;
    private AccountType accountType;
    private int accountNumber;
    private double currentBalance;

    @OneToMany(mappedBy = "bankAccount")
    private List<Transaction> transactionHistory = new ArrayList<Transaction>();

}
