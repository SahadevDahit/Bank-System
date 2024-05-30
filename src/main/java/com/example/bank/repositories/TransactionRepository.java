package com.example.bank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bank.entities.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity,String> {

	List<TransactionEntity> findByAccount_AccountNo(String accountNo);

}
