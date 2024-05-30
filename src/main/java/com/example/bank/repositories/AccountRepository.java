package com.example.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bank.entities.AccountEntity;

@Repository
 public interface AccountRepository extends JpaRepository<AccountEntity,String> {
	
	AccountEntity findByAccountNo(String accountNo);
}
