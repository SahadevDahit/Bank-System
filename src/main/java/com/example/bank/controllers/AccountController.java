package com.example.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.entities.AccountEntity;
import com.example.bank.entities.UsersEntity;
import com.example.bank.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	 @Autowired
		private UsersController userController;
	 
	 
	 @PostMapping("/account")
	    public ResponseEntity<String> addAccount(@RequestBody AccountEntity accountEntity) {
		  try {
	            UsersEntity user = userController.getUserFromToken();
	            if (user == null) {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not found");
	            }
	            accountEntity.setUser(user);
	            ResponseEntity<String> newAccount = accountService.addNewAccount(accountEntity);
	            return ResponseEntity.status(HttpStatus.CREATED).body("");
	        } catch (IllegalArgumentException e) {
	            log.error("Validation error: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        } catch (Exception e) {
	            log.error("Error occurred while adding account: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding account");
	        }
	 
	    }
	 
	 @GetMapping("/account/{accountNo}")
	    public ResponseEntity<?> getAccountByAccountNo(@PathVariable String accountNo) {
	        try {
	            // Call the service method to retrieve account details by account number
	            AccountEntity account = accountService.getAccountByAccountNo(accountNo);
	            if (account == null) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
	            }
	            return ResponseEntity.ok(account);
	        } catch (Exception e) {
	            log.error("Error occurred while fetching account details: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching account details");
	        }
	    }

	 @PatchMapping("/account/{accountId}")
	    public ResponseEntity<AccountEntity> updateAccount(@PathVariable String accountId, @RequestBody AccountEntity accountDetails) {
	        try {
	        	 UsersEntity user = userController.getUserFromToken();
		            if (user == null) {
		                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		            }
		            if (!"admin".equals(user.getRole())) {
		                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		            }
	            AccountEntity updatedAccount = accountService.updateAccount(accountId, accountDetails);
	            return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
	        } catch (Exception e) {
	            log.error("Account not found: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        } 
	    }

}
