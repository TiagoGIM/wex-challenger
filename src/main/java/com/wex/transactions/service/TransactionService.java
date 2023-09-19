package com.wex.transactions.service;

import com.wex.transactions.dto.TransactionResponse;
import com.wex.transactions.model.Transaction;
import com.wex.transactions.repository.TransactionRepository;

import java.util.Optional;
import java.util.UUID;

public class TransactionService implements ITransactionService {
    public TransactionService(TransactionRepository repository) {
    }

    @Override
    public Transaction save(Transaction transaction) {
        return null;
    }

    @Override
    public Optional<TransactionResponse> convertedTransaction(UUID id, String currency) {
        return Optional.empty();
    }
}
