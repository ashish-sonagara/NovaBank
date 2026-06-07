package com.ashish.BankManagement.exception;

public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException(String e){
        super(e);
    }
}
