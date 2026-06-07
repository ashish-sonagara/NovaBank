package com.ashish.BankManagement.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String e){
        super(e);
    }
}