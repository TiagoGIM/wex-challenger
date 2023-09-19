package com.wex.transactions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Transaction {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;

        @Size(min=10, max = 50, message = "Description must not exceed 50 characters.")
        @Column(nullable = false)
        private String description;

        @Column(nullable = false)
        private LocalDate date;

        @Column(nullable = false)
        private BigDecimal purchaseAmount;

        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(nullable = false)
        private Date createdDate;


        public void setDate(LocalDate date) {
            if (date != null) {
                this.date = date;
            } else {
                throw new IllegalArgumentException("Transaction date must be a valid date.");
            }
        }

        public void setPurchaseAmount(double purchaseAmount) {
            this.purchaseAmount = BigDecimal.valueOf(purchaseAmount).setScale(2, RoundingMode.HALF_UP);
        }
    }

