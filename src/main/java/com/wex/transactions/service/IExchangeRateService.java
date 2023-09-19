package com.wex.transactions.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IExchangeRateService {
    public BigDecimal getExchangeRate(LocalDate purchaseDate, String currency);
}
