package com.example.bank.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @Column(name = "id")
    @NotNull(message = "id cannot be null")
    private String id = UUID.randomUUID().toString();

    		@ManyToOne(fetch = FetchType.EAGER)
		    @JoinColumn(name = "account_id", nullable = false)
    		@JsonIgnoreProperties({"transactions", "user","bank"})
		    private AccountEntity account;

	    @Column(name = "transaction_type")
	    @NotNull(message = "transaction cannot be null")
	    private String transactionType;

	    @Column(name = "amount")
	    @NotNull(message = "amount cannot be null")
	    private BigDecimal amount;

	    @NotNull(message = "Transaction date cannot be null")
	    @Column(name = "transaction_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	    private LocalDateTime transactionDate;

}
