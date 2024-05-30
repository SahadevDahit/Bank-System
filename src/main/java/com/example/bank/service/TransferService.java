package com.example.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bank.entities.AccountEntity;
import com.example.bank.entities.TransferEntity;
import com.example.bank.repositories.AccountRepository;
import com.example.bank.repositories.TransferRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransferService {
	
	@Autowired
	private TransferRepository transferRepository;
	
	 @Autowired
	    private AccountRepository accountRepository;
	 
	 @Autowired
	 private AccountService accountService;
	 
	
	
	 public String transferAmount(TransferEntity transferRequest,String senderPinCode) {
		
	        AccountEntity senderAccount = accountRepository.findByAccountNo(transferRequest.getSenderAccount());                
	        AccountEntity receiverAccount = accountRepository.findByAccountNo(transferRequest.getReceiverAccount());
	        
	        if(senderAccount==null && receiverAccount==null) {
	            	throw new IllegalArgumentException("Invalid the sender or receiver account");
	            }
	        
	        log.info("pin code -->"+senderAccount.getPinCode());
	        log.info("pin code--> "+ senderPinCode);
	        
	       
	        if (!senderPinCode.equals(senderAccount.getPinCode())) {
	            throw new IllegalArgumentException("Invalid PIN code.");
	        }

	        if (senderAccount.getBalance().compareTo(transferRequest.getAmount()) < 0) {
	            throw new IllegalArgumentException("Insufficient balance in sender's account.");
	        }

	        // Perform the transfer
	        senderAccount.setBalance(senderAccount.getBalance().subtract(transferRequest.getAmount()));
	        receiverAccount.setBalance(receiverAccount.getBalance().add(transferRequest.getAmount()));

	        // Save the updated accounts
	        accountRepository.save(senderAccount);
	        accountRepository.save(receiverAccount);
	        transferRepository.save(transferRequest);
	        
	        return "Transfer successful!";
	    }

}
