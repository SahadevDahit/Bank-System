package com.example.bank.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {


    @Id
    @Column(name = "id")
    @NotNull(message = "id cannot be null")
    private String id = UUID.randomUUID().toString();

    @Column(name = "account_no", unique = true)
    @NotNull(message = "Account number cannot be null")
    @Size(min = 10, message = "Account number must be at least 10 characters long")
    private String accountNo;


    @Column(name = "account_type")
    @NotNull(message = "Account type cannot be null")
     private String accountType;

    @Column(name = "balance")
    @NotNull(message = "Balance cannot be null")
    private BigDecimal balance; // Use BigDecimal for monetary values

    @Column(name = "date_created")
    @NotNull(message = "Creation date cannot be null")
    private Date dateCreated = new Date(); // Initialize with current date

    @Column(name = "credit_limit")
    private BigDecimal creditLimit; // Optional credit limit
    
    @Column(name = "pinCode")
    @NotNull(message = "pin code cannot be null")
    private String pinCode;
    
    @Column(name = "status")
    private boolean status = false; // Default status set to false


    @OneToOne
    @JsonIgnoreProperties("account")
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UsersEntity user;
    
//    @ManyToOne // Many accounts to one bank (recommended)
//    @JoinColumn(name = "bank_id", nullable = false) // Foreign key to BankEntity
//    private BankEntity bank;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TransactionEntity> transactions = new ArrayList<>();
    
    // Getter and Setter methods for status
    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
