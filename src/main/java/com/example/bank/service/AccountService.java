package com.example.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.bank.controllers.UsersController;
import com.example.bank.entities.AccountEntity;
import com.example.bank.entities.UsersEntity;
import com.example.bank.repositories.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	  
   	

    public ResponseEntity<String> addNewAccount(AccountEntity accountEntity) {  	 
      
        accountEntity.setStatus(false); // Set default status to inactive
        AccountEntity savedAccount = accountRepository.save(accountEntity);
        
        if (savedAccount != null) {
            // Return a ResponseEntity with status 200 and a custom message
            return ResponseEntity.ok("Account saved successfully");
        } else {
            // Handle the case where the account was not saved
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save account");
        }
    }

    public AccountEntity getAccountByAccountNo(String accountNo) {
        // Call the repository method to find the account by its account number
        return accountRepository.findByAccountNo(accountNo);
    }
    
    
	    public AccountEntity updateAccount(String accountId, AccountEntity accountDetails) {
	        if (accountDetails == null) {
	            throw new IllegalArgumentException("Account details cannot be null");
	        }

	        Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);

	        if (optionalAccount.isPresent()) {
	            AccountEntity existingAccount = optionalAccount.get();

	            // Update specific fields only if values are provided in accountDetails
	            if (accountDetails.getAccountNo() != null) {
	                existingAccount.setAccountNo(accountDetails.getAccountNo());
	            }
	            if (accountDetails.getAccountType() != null) {
	                existingAccount.setAccountType(accountDetails.getAccountType());
	            }
	            if (accountDetails.getBalance() != null) {
	                existingAccount.setBalance(accountDetails.getBalance());
	            }
	            if (accountDetails.getCreditLimit() != null) {
	                existingAccount.setCreditLimit(accountDetails.getCreditLimit());
	            }
	            if (accountDetails.getStatus()) { // Check for status update
	                existingAccount.setStatus(accountDetails.getStatus());
	            }

	            return accountRepository.save(existingAccount);
	        } else {
	            throw new IllegalArgumentException("Account with ID " + accountId + " not found");
	        }
	    }


}

