package com.example.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bank.entities.BankEntity;


@Repository
public interface  BankRepository extends JpaRepository<BankEntity, String> {

}
