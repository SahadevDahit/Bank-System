package com.example.bank.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.entities.AccountEntity;
import com.example.bank.entities.TransactionEntity;
import com.example.bank.service.AccountService;
import com.example.bank.service.TransactionService;



@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService; 
	

	@PostMapping("/transactions/{account_no}")
	public ResponseEntity<String> generateTransaction(@PathVariable("account_no") String accountNo, @RequestBody TransactionEntity transaction) {
	    try {
	        // Find the account details using the provided account number
	        AccountEntity account = accountService.getAccountByAccountNo(accountNo);
	        
	        // Check if the account exists
	        if (account == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
	        }
	        ArrayList<String> transactionType=new ArrayList<>();
	        transactionType.add("deposit");
	        transactionType.add("withdraw");
	        
	        if(!transactionType.contains(transaction.getTransactionType())) {
	        	 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid transaction.");
	        }

	        // Check if the transaction type is "withdraw"
	        if ("withdraw".equalsIgnoreCase(transaction.getTransactionType())) {
	            // Check if the balance is sufficient for withdrawal
	            if (account.getBalance().compareTo(transaction.getAmount()) < 0) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance for withdrawal.");
	            }
	        }

	        // Set the account for the transaction
	        transaction.setAccount(account);

	        // Call the service method to generate the transaction
	        TransactionEntity newTransaction = transactionService.generateTransaction(transaction);

	        // Return a success response with the generated transaction ID
	        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction generated successfully. ID: " + newTransaction.getId());
	    } catch (Exception e) {
	        // Log the error
	        e.printStackTrace();

	        // Return an error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate transaction.");
	    }
	}

	
	 @GetMapping("/transactions/{accountNo}")
	 public ResponseEntity<List<TransactionEntity>> getAllTransactionsByAccountNo(@PathVariable String accountNo) {
	        List<TransactionEntity> transactions = transactionService.getAllTransactionsByAccountNo(accountNo);
	        if (transactions.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(transactions, HttpStatus.OK);
	    }
}
