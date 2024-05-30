package com.example.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bank.entities.TransferEntity;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity,String> {

}
