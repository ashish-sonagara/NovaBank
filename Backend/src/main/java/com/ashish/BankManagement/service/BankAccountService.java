package com.ashish.BankManagement.service;

import java.lang.foreign.Linker.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashish.BankManagement.dto.response.TransactionDTO;
import com.ashish.BankManagement.enums.TransactionStatus;
import com.ashish.BankManagement.enums.TransactionType;
import com.ashish.BankManagement.exception.AccountNotFoundException;
import com.ashish.BankManagement.exception.InsufficientBalanceException;
import com.ashish.BankManagement.exception.InvalidAmountException;
import com.ashish.BankManagement.exception.InvalidTransactionException;
import com.ashish.BankManagement.model.BankAccount;
import com.ashish.BankManagement.model.Transaction;
import com.ashish.BankManagement.repository.BankRepo;
import com.ashish.BankManagement.repository.TransactionRepo;

@Service
public class BankAccountService {

    // NEED SOME IMRPOVEMENTS IN THIS FILE TOO

    @Autowired
    BankRepo bankRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Transactional
    public String depositAmount(Long accountNumber, double amount) {
        if (amount <= 0){
            // createFailedTransaction(accountNumber , amount, TransactionType.DEPOSIT);   // should not be casue the transaction amount is not failed here but its invalid
            throw new InvalidAmountException("Amount must be greater than 0");
        }

        Optional<BankAccount> account = this.bankRepo.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new AccountNotFoundException("Bank Account Not Found!");
            // return new ResponseEntity<>("Bank Account Not Found!", HttpStatus.BAD_REQUEST);
        }
        BankAccount bankAccount = account.get();

        double currBalance = bankAccount.getCurrentBalance();
        currBalance += amount;
        bankAccount.setCurrentBalance(currBalance);

        Transaction t = new Transaction();
        t.setTransactionType(TransactionType.DEPOSIT);
        t.setBankAccount(bankAccount);
        t.setTransactionAmount(amount);
        t.setDate(LocalDateTime.now());
        t.setTransactionStatus(TransactionStatus.SUCCESS);

        List<Transaction> history = bankAccount.getTransactionHistory();
        history.add(t);
        bankAccount.setTransactionHistory(history);

        this.transactionRepo.save(t);
        this.bankRepo.save(bankAccount);
        return "Amount: " + amount + " Deposited Succesfully!";
    }

    @Transactional
    public String withdrawAmount(Long accountNumber, double amount) {
        if (amount < 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
        }

        Optional<BankAccount> account = this.bankRepo.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new AccountNotFoundException("Bank Account Not Found!");
        }
        BankAccount bankAccount = account.get();
        

        if (bankAccount.getCurrentBalance() < amount) {
            createFailedTransaction(bankAccount , amount , TransactionType.WITHDRAW);
            throw new InsufficientBalanceException("Not Enough balance for Withdrawal!");
        }

        double newBalance = bankAccount.getCurrentBalance() - amount;
        bankAccount.setCurrentBalance(newBalance);

        Transaction t = new Transaction();
        t.setBankAccount(bankAccount);
        t.setDate(LocalDateTime.now());
        t.setTransactionType(TransactionType.WITHDRAW);
        t.setTransactionAmount(amount);
        t.setTransactionStatus(TransactionStatus.SUCCESS);

        List<Transaction> history = bankAccount.getTransactionHistory();
        history.add(t);
        bankAccount.setTransactionHistory(history);

        this.transactionRepo.save(t);
        this.bankRepo.save(bankAccount);
        return "Amount: " + amount + " Withdrawn Succesfully!";
    }

    @Transactional // the annotation is used to roll back the operation in the Crash outs
    public String transferMoney(Long accountNumber1, Long accountNumber2, double amount) {
        if (accountNumber1.equals(accountNumber2)) { // Always use the .equals to compare anything , only use == to comapre the primitive values
            throw new InvalidTransactionException("Amount cannot be transferred into the same account");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
            // return new ResponseEntity<>("Amount can not be Lesser than 0", HttpStatus.BAD_REQUEST);
        }
        
        Optional<BankAccount> account1 = this.bankRepo.findByAccountNumber(accountNumber1);
        if (account1.isEmpty()) {
            throw new AccountNotFoundException("Bank Account Not Found!");
            // return new ResponseEntity<>("Bank Account Not Found!", HttpStatus.BAD_REQUEST);
        }
        BankAccount bankAccount1 = account1.get();

        Optional<BankAccount> account2 = this.bankRepo.findByAccountNumber(accountNumber2);
        if (account2.isEmpty()) {
            throw new AccountNotFoundException("Bank Account Not Found!");
            // return new ResponseEntity<>("Bank Account Not Found!", HttpStatus.BAD_REQUEST);
        }
        BankAccount bankAccount2 = account2.get();

        if (bankAccount1.getCurrentBalance() < amount) {
            createFailedTransaction(bankAccount1, amount, TransactionType.DEBIT);
            createFailedTransaction(bankAccount2, amount, TransactionType.CREDIT);
            throw new InsufficientBalanceException("Not Enough balance for Transaction!");
            // return new ResponseEntity<>("Not Enough Balance in the Account", HttpStatus.BAD_REQUEST);
        }

        Transaction debit = createDebitTransaction(bankAccount1, amount);
        Transaction credit = createCreditTransaction(bankAccount2, amount);

        this.transactionRepo.save(debit);
        this.transactionRepo.save(credit);

        this.bankRepo.save(bankAccount1);
        this.bankRepo.save(bankAccount2);
        return "Money Transferred Success Fully";
    }

    public Transaction createDebitTransaction(BankAccount bankAccount, double amount){
    
        bankAccount.setCurrentBalance(bankAccount.getCurrentBalance() - amount);

        Transaction debit = new Transaction();
        debit.setBankAccount(bankAccount);
        debit.setTransactionAmount(amount);
        debit.setDate(LocalDateTime.now());
        debit.setTransactionType(TransactionType.DEBIT);        
        debit.setTransactionStatus(TransactionStatus.SUCCESS);

        List<Transaction> historyAccount1 = bankAccount.getTransactionHistory();
        historyAccount1.add(debit);
        bankAccount.setTransactionHistory(historyAccount1);

        return debit;
   
    }

    public Transaction createCreditTransaction(BankAccount bankAccount, double amount) {

        bankAccount.setCurrentBalance(bankAccount.getCurrentBalance() + amount);

        Transaction credit = new Transaction();
        credit.setBankAccount(bankAccount);
        credit.setTransactionAmount(amount);
        credit.setDate(LocalDateTime.now());
        credit.setTransactionType(TransactionType.CREDIT);
        credit.setTransactionStatus(TransactionStatus.SUCCESS);
        
        List<Transaction> historyAccount2 = bankAccount.getTransactionHistory();
        historyAccount2.add(credit);
        bankAccount.setTransactionHistory(historyAccount2);
        
        return credit;
    }

    public void createFailedTransaction(BankAccount bankAccount, Double amount , TransactionType transactionType){  // wont work , DUE to @Transactional annotation which roll backs everything on failure.

        Transaction t = new Transaction();
        t.setTransactionType(transactionType);
        t.setBankAccount(bankAccount);
        t.setTransactionAmount(amount);
        t.setDate(LocalDateTime.now());
        t.setTransactionStatus(TransactionStatus.FAILED);

        List<Transaction> history = bankAccount.getTransactionHistory();
        history.add(t);
        bankAccount.setTransactionHistory(history);

        this.transactionRepo.save(t);
        this.bankRepo.save(bankAccount);
    }

    public List<TransactionDTO> fetchAllTransaction(){
        List<Transaction> transactionList = this.transactionRepo.findAll();
        List<TransactionDTO> transactionDTOList = new ArrayList<>();

        for (Transaction transaction : transactionList){
            TransactionDTO transactionDTO = new TransactionDTO();

            transactionDTO.setDate(transaction.getDate());
            transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
            transactionDTO.setTransactionId(transaction.getTransactionId());
            transactionDTO.setTransactionStatus(transaction.getTransactionStatus());
            transactionDTO.setTransactionType(transaction.getTransactionType());
            transactionDTO.setAccountOwner(transaction.getBankAccount().getAccountOwner());
            transactionDTO.setBankName(transaction.getBankAccount().getBankName());
            transactionDTO.setAccountNumber(transaction.getBankAccount().getAccountNumber());

            transactionDTOList.add(transactionDTO);
        }

        return transactionDTOList;
    }
    
    public List<TransactionDTO> fetchLastNTransaction(Integer n){
        List<Transaction> transactionList = this.transactionRepo.findAll();
        List<TransactionDTO> transactionDTOList = new ArrayList<>();

        for (Transaction transaction : transactionList){
            if (n == 0){
                break;
            }

            TransactionDTO transactionDTO = new TransactionDTO();

            transactionDTO.setDate(transaction.getDate());
            transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
            transactionDTO.setTransactionId(transaction.getTransactionId());
            transactionDTO.setTransactionStatus(transaction.getTransactionStatus());
            transactionDTO.setTransactionType(transaction.getTransactionType());
            transactionDTO.setAccountOwner(transaction.getBankAccount().getAccountOwner());
            transactionDTO.setBankName(transaction.getBankAccount().getBankName());
            transactionDTO.setAccountNumber(transaction.getBankAccount().getAccountNumber());

            transactionDTOList.add(transactionDTO);

            n -= 1;
        }

        return transactionDTOList;
    }

}
