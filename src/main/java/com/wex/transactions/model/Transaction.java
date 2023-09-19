package com.wex.transactions.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

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

        public void setDescription(String description) {
            if (description != null && description.length() <= 50) {
                this.description = description;
            } else {
                throw new IllegalArgumentException("Description must not exceed 50 characters.");
            }
        }

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

