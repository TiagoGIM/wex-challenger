package com.wex.transactions.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionRequest {
    @Size(min=10, max = 50, message = "Description must not exceed 50 characters.")
    private String description;
    @NotNull
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format. Use yyyy-MM-dd.")
    private String date;
    @Positive(message = "Purchase amount must be a positive value.")
    private BigDecimal purchaseAmount;

}