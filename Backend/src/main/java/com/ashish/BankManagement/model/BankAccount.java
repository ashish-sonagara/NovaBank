package com.ashish.BankManagement.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ashish.BankManagement.enums.AccountStatus;
import com.ashish.BankManagement.enums.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(unique = true, nullable = false)
    private Long accountNumber;
    private double currentBalance;
    private String bankName;
    private String email;
    private double phoneNumber;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @OneToMany(mappedBy = "bankAccount")
    private List<Transaction> transactionHistory = new ArrayList<Transaction>();

}
