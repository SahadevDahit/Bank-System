package com.example.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.entities.BankEntity;
import com.example.bank.service.BankService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/bank")
    public  ResponseEntity<BankEntity> addNewBank(@RequestBody BankEntity bankEntity) {
       return  bankService.AddNewBank(bankEntity);
    }

    @PutMapping("/bank/{id}")
    public ResponseEntity<BankEntity> updateBank(@PathVariable String id, @RequestBody BankEntity bankDetails) {
        return bankService.updateBank(id, bankDetails);
    }
    @GetMapping("/bank/{bankId}")
    public ResponseEntity<BankEntity> getBankById(@PathVariable String bankId) {
        try {
            BankEntity bank = bankService.getBankById(bankId);
            if (bank != null) {
                return ResponseEntity.status(HttpStatus.OK).body(bank);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching bank: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
