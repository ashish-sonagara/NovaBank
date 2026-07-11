package com.ashish.BankManagement.exception;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String e){
        super(e);
    }
}
