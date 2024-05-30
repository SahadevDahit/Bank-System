package com.example.bank.entities;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name = "users")
public class UsersEntity {

    @Id
    @Column(name = "id")
    @NotNull(message = "id cannot be null")
    private String id = UUID.randomUUID().toString();

    @Column(name = "email", unique = true) // Ensuring the email is unique
    @NotNull(message = "email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    @NotNull(message = "password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long") // Ensuring minimum length of 8 characters
    private String password;
    
    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    @NotNull(message = "phone number cannot be null")
    private String phoneNumber;

    @NotNull(message = "DOB cannot be null")
    @Column(name = "dob")
    private Date dob;

    @Column(name = "role")
    @NotNull(message = "Role cannot be null")
    private String role;

    @Column(name = "status")
    @NotNull(message = "Status cannot be null")
    private Boolean status = true;
    

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private BankEntity bank;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private AccountEntity account;
    
    public void setPassword(String password) {
        // Use BCryptPasswordEncoder to encode the password before setting
        if (password != null && !password.isEmpty()) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            this.password = passwordEncoder.encode(password);
        } else {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }
}
