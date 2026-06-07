package com.ashish.BankManagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashish.BankManagement.enums.TransactionType;
import com.ashish.BankManagement.exception.AccountNotFoundException;
import com.ashish.BankManagement.exception.InsufficientBalanceException;
import com.ashish.BankManagement.exception.InvalidAmountException;
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
    public ResponseEntity<String> depositAmount(int id, double amount) {
        if (amount <= 0){
            throw new InvalidAmountException("Amount must be greater than 0");
        }

        Optional<BankAccount> account = this.bankRepo.findById(id);
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

        List<Transaction> history = bankAccount.getTransactionHistory();
        history.add(t);
        bankAccount.setTransactionHistory(history);

        this.transactionRepo.save(t);
        this.bankRepo.save(bankAccount);
        return new ResponseEntity<>("Amount Deposited Succesfully!", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> withdrawAmount(Integer accountID, double amount) {
        if (amount < 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
            // return new ResponseEntity<>("Amount can not be Lesser than 0", HttpStatus.BAD_REQUEST);
        }

        Optional<BankAccount> account = this.bankRepo.findById(accountID);
        if (account.isEmpty()) {
            throw new AccountNotFoundException("Bank Account Not Found!");
            // return new ResponseEntity<>("Bank Account Not Found!", HttpStatus.BAD_REQUEST);
        }
        BankAccount bankAccount = account.get();
        

        if (bankAccount.getCurrentBalance() < amount) {
            throw new InsufficientBalanceException("Not Enough balance for Withdrawal!");
            // return new ResponseEntity<>("Not Enough Balance in the Account", HttpStatus.BAD_REQUEST);
        }

        double newBalance = bankAccount.getCurrentBalance() - amount;
        bankAccount.setCurrentBalance(newBalance);

        Transaction t = new Transaction();
        t.setBankAccount(bankAccount);
        t.setDate(LocalDateTime.now());
        t.setTransactionType(TransactionType.WITHDRAW);
        t.setTransactionAmount(amount);

        List<Transaction> history = bankAccount.getTransactionHistory();
        history.add(t);
        bankAccount.setTransactionHistory(history);

        this.transactionRepo.save(t);
        this.bankRepo.save(bankAccount);
        return new ResponseEntity<>("Withdrawn Succesfully!", HttpStatus.OK);
    }

    @Transactional // the annotation is used to roll back the operation in the Crash outs
    public ResponseEntity<String> transferMoney(Integer accountID_1, Integer accountID_2, double amount) {
        if (accountID_1.equals(accountID_2)) { // Always use the .equals to compare anything , only use == to comapre the primitive values
            return new ResponseEntity<>("Amount can not be Transferred into same Account", HttpStatus.BAD_REQUEST);
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
            // return new ResponseEntity<>("Amount can not be Lesser than 0", HttpStatus.BAD_REQUEST);
        }
        
        Optional<BankAccount> account1 = this.bankRepo.findById(accountID_1);
        if (account1.isEmpty()) {
            throw new AccountNotFoundException("Bank Account Not Found!");
            // return new ResponseEntity<>("Bank Account Not Found!", HttpStatus.BAD_REQUEST);
        }
        BankAccount bankAccount1 = account1.get();

        Optional<BankAccount> account2 = this.bankRepo.findById(accountID_2);
        if (account2.isEmpty()) {
            throw new AccountNotFoundException("Bank Account Not Found!");
            // return new ResponseEntity<>("Bank Account Not Found!", HttpStatus.BAD_REQUEST);
        }
        BankAccount bankAccount2 = account2.get();

        if (bankAccount1.getCurrentBalance() < amount) {
            throw new InsufficientBalanceException("Not Enough balance for Transaction!");
            // return new ResponseEntity<>("Not Enough Balance in the Account", HttpStatus.BAD_REQUEST);
        }

        Transaction debit = createDebitTransaction(bankAccount1, amount);
        Transaction credit = createCreditTransaction(bankAccount2, amount);

        this.transactionRepo.save(debit);
        this.transactionRepo.save(credit);

        this.bankRepo.save(bankAccount1);
        this.bankRepo.save(bankAccount2);
        return new ResponseEntity<>("Money Transferred Success Fully", HttpStatus.OK);
    }

    public Transaction createDebitTransaction(BankAccount bankAccount, double amount){
    
        bankAccount.setCurrentBalance(bankAccount.getCurrentBalance() - amount);

        Transaction debit = new Transaction();
        debit.setBankAccount(bankAccount);
        debit.setTransactionAmount(amount);
        debit.setDate(LocalDateTime.now());
        debit.setTransactionType(TransactionType.DEBIT);        

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

        List<Transaction> historyAccount2 = bankAccount.getTransactionHistory();
        historyAccount2.add(credit);
        bankAccount.setTransactionHistory(historyAccount2);
        
        return credit;
    }

}
