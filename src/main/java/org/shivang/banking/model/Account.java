package org.shivang.banking.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)
    String accountNumber;
    @Column(nullable = false)
    BigDecimal balance;
    @Enumerated(EnumType.STRING)
    AccountStatus status;
    @Enumerated(EnumType.STRING)
    AccountType  accountType;
    LocalDateTime createdAt;

}
