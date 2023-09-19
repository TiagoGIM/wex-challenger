package com.wex.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    public UUID id;
    private String description;
    private LocalDate date;
    private BigDecimal purchaseAmount;
    private BigDecimal convertedAmount;
    private BigDecimal exchangeRate;

}
