package com.example.bank.entities;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "banks")
public class BankEntity {

    @Id
    @Column(name = "id")
    @NotNull(message = "id cannot be null")
    private String id = UUID.randomUUID().toString();

    @Column(name = "name")
    @NotNull(message = "name cannot be null")
    private String name;

    @Column(name = "address")
    @NotNull(message = "address cannot be null")
    private String address;

    @Column(name = "location")
    @NotNull(message = "location cannot be null")
    private String location;

    @Column(name = "contact")
    @NotNull(message = "contact cannot be null")
    private String contact;

    @Column(name = "email", unique = true)
    @NotNull(message = "email cannot be null")
    private String email;
    
    
    @Column(name = "status")
    @NotNull(message = "status cannot be null")
    private Boolean status;
    
    @OneToOne
    @JsonIgnoreProperties("bank")
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UsersEntity user;
    
//    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL) // One bank to many accounts
//    @JsonIgnore // Excluding accounts here (optional)
//    private List<AccountEntity> accounts = new ArrayList<>();

}