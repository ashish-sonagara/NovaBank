package com.ashish.BankManagement.exception;

public class ImproperAccountDetailsException extends RuntimeException{
    public ImproperAccountDetailsException(String e){
        super(e);
    }
}
