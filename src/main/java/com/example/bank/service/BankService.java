package com.example.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.bank.controllers.UsersController;
import com.example.bank.entities.BankEntity;
import com.example.bank.entities.UsersEntity;
import com.example.bank.repositories.BankRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;
    
    @Autowired
    private UsersController userController;

  
    public ResponseEntity<BankEntity> AddNewBank(BankEntity bankEntity) {
        try {
            UsersEntity user = userController.getUserFromToken();
            if(user==null) {
            	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            bankEntity.setUser(user);
            if (!"admin".equals(user.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            BankEntity savedBank= bankRepository.save(bankEntity);
            if (savedBank != null) {
				return new ResponseEntity<>(savedBank, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			// Handle the exception and return an appropriate response
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }


    public ResponseEntity<BankEntity> updateBank(String bankId, BankEntity bankDetails) {
    	 if (bankDetails == null) {
             throw new IllegalArgumentException("Bank details cannot be null");
         }
         try {
             UsersEntity user = userController.getUserFromToken();
             if (user == null) {
                 throw new RuntimeException("Unauthorized access");
             }
             if (!"admin".equals(user.getRole())) {
                 return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
             }

             Optional<BankEntity> optionalBankEntity = bankRepository.findById(bankId);

             if (optionalBankEntity.isPresent()) {
                 BankEntity existingBank = optionalBankEntity.get();
                 // Update fields only if values are provided in bankDetails
                 if (bankDetails.getName() != null) {
                     existingBank.setName(bankDetails.getName());
                 }
                 if (bankDetails.getAddress() != null) {
                     existingBank.setAddress(bankDetails.getAddress());
                 }
                 if (bankDetails.getLocation() != null) {
                     existingBank.setLocation(bankDetails.getLocation());
                 }
                 if (bankDetails.getContact() != null) {
                     existingBank.setContact(bankDetails.getContact());
                 }
                 if (bankDetails.getEmail() != null) {
                     existingBank.setEmail(bankDetails.getEmail());
                 }
                 if (bankDetails.getStatus() != null) {
                     existingBank.setStatus(bankDetails.getStatus());
                 }

                 return ResponseEntity.status(HttpStatus.OK).body(bankRepository.save(existingBank));
             } else {
                 throw new IllegalArgumentException("Bank with ID " + bankId + " not found");
             }
         } catch (IllegalArgumentException e) {
             log.error("Validation error: {}", e.getMessage(), e);
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
         } catch (Exception e) {
             log.error("Error occurred while updating bank with ID {}: {}", bankId, e.getMessage(), e);
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
         }
     
    }



public BankEntity getBankById(String bankId) {
    Optional<BankEntity> optionalBank = bankRepository.findById(bankId);
    return optionalBank.orElse(null);
}

}