package com.wex.transactions.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionRequest {
    @Size(min=10, max = 50, message = "Description must not exceed 50 characters.")
    private String description;
    private LocalDate date;
    private BigDecimal purchaseAmount;
}