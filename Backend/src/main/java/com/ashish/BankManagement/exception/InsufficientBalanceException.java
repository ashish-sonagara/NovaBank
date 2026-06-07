package com.ashish.BankManagement.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String e) {
        super(e);
    }
}
