package com.wex.transactions.controller;

import com.wex.transactions.dto.TransactionDTO;
import com.wex.transactions.dto.TransactionRequest;
import com.wex.transactions.dto.TransactionResponse;
import com.wex.transactions.exceptions.ExchangeRateProviderException;
import com.wex.transactions.model.Transaction;
import com.wex.transactions.service.TransactionService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final ModelMapper modelMapper;
    @Autowired
    public TransactionController(TransactionService transactionService, ModelMapper modelMapper) {
        this.transactionService = transactionService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDTO create(@RequestBody @Valid TransactionRequest transactionRequest) {
        // Create a Transaction object from the request
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(transactionRequest.getDate(), formatter);

            Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
            transaction.setDate(parsedDate);

            // Call the service to create the transaction
            Transaction createdTransaction = transactionService.save(transaction);

            return modelMapper.map(createdTransaction, TransactionDTO.class);
        }catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage() );
        }

    }

    @GetMapping("/convert/{currency}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse convertedPurchase(@PathVariable("currency") String currency,
                                                 @PathVariable("id") UUID id) {

        return transactionService.convertedTransaction(id , currency)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction id not Found" ));
    }
}
