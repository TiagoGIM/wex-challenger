package com.wex.transactions.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ExchangeRateService implements IExchangeRateService {
    @Override
    public BigDecimal getExchangeRate(LocalDate purchaseDate, String currency) {
        return null;
    }
}
