package com.example.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.bank.controllers.UsersController;
import com.example.bank.entities.AccountEntity;
import com.example.bank.entities.TransactionEntity;
import com.example.bank.entities.UsersEntity;
import com.example.bank.repositories.AccountRepository;
import com.example.bank.repositories.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
    @Autowired
    private UsersController userController;
    
    public TransactionEntity generateTransaction(TransactionEntity transaction) {
        // Retrieve user from token (assuming you have a userController method)
        UsersEntity user = userController.getUserFromToken();
        if (user == null) {
            return null; // Handle user not found scenario
        }

        // Fetch the account associated with the user
        AccountEntity account = user.getAccount();
        if (account == null) {
            return null; // Handle account not found scenario
        }

        // Update account balance based on transaction type
        if (transaction.getTransactionType().equalsIgnoreCase("deposit")) {
            account.setBalance(account.getBalance().add(transaction.getAmount()));
        } else if (transaction.getTransactionType().equalsIgnoreCase("withdraw")) {
            // Check for sufficient funds before withdrawal
            if (account.getBalance().compareTo(transaction.getAmount()) >= 0) {
                account.setBalance(account.getBalance().subtract(transaction.getAmount()));
            } else {
            	 throw new IllegalArgumentException("Insufficient amount ");
            }
        } else {
            // Handle invalid transaction type (return error or throw exception)
            throw new IllegalArgumentException("Invalid transaction type: " + transaction.getTransactionType());
        }

        // Save the updated account and new transaction
        accountRepository.save(account);
        TransactionEntity newTransaction = transactionRepository.save(transaction);

        return newTransaction;
    }

    public List<TransactionEntity> getAllTransactionsByAccountNo(String accountNo) {
        return transactionRepository.findByAccount_AccountNo(accountNo);
    }

	
	
}
