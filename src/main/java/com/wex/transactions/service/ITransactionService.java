package com.wex.transactions.service;

import com.wex.transactions.dto.TransactionResponse;
import com.wex.transactions.model.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface ITransactionService {
    Transaction save(Transaction transaction);
    Optional<TransactionResponse> convertedTransaction(UUID id, String currency);
}
