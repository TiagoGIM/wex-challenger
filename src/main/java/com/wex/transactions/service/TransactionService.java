package com.wex.transactions.service;

import com.wex.transactions.dto.TransactionResponse;
import com.wex.transactions.exceptions.ExchangeRateProviderException;
import com.wex.transactions.model.Transaction;
import com.wex.transactions.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository repository;
    private final ExchangeRateService exchangeRateService;
    private final ModelMapper modelMapper;
    @Autowired
    public TransactionService(
            TransactionRepository repository,
            ExchangeRateService exchangeRateService,
            ModelMapper modelMapper) {
        this.repository = repository;
        this.exchangeRateService = exchangeRateService;
        this.modelMapper = modelMapper;
    }

    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public Optional<TransactionResponse> convertedTransaction(UUID id, String currency) {
        return repository.findById(id).map( transaction ->
        {
            LocalDate purchasedDate = transaction.getDate();

            BigDecimal exchangeRate =  exchangeRateService.getExchangeRate(purchasedDate, currency);

            TransactionResponse response = modelMapper.map(transaction, TransactionResponse.class);

            BigDecimal convertedAmount = transaction.getPurchaseAmount()
                    .multiply(exchangeRate)
                    .setScale(2, RoundingMode.HALF_UP);

            response.setConvertedAmount(convertedAmount);
            response.setExchangeRate(exchangeRate);

            return response;
        });


    }
}
