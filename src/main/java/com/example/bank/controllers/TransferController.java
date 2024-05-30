package com.example.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.entities.TransferEntity;
import com.example.bank.service.TransferService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TransferController {
	
	@Autowired
	private TransferService transferService;

	
	@PostMapping("/transfer/{pincode}")
	public String transferAmount(
	        @PathVariable String pincode, // Extract pincode from path variable
	        @RequestBody TransferEntity transferEntity) {     	
	     return transferService.transferAmount(transferEntity, pincode); // Pass pincode to service  
	  }
	
}
