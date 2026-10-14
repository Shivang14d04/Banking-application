package org.shivang.banking.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "from_account_id",nullable = false)
    Account fromAccount;
    @ManyToOne
    @JoinColumn(name = "to_account_id",nullable = false)
    Account toAccount;
    @Column(nullable = false)
    BigDecimal amount;
    LocalDateTime createdAt;
    LocalDateTime completedAt;
    @Enumerated(EnumType.STRING)
    TransferStatus status;
}
