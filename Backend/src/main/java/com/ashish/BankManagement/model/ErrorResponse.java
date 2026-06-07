package com.ashish.BankManagement.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    
    private LocalDateTime errorTime;
    private int status;
    private String error;
    private String message;
    
}
