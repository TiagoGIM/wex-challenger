package com.wex.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionDTO {
    private UUID id;
    private String description;
    private LocalDate date;
    private BigDecimal purchaseAmount;
}