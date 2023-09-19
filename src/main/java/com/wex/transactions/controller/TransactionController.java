package com.wex.transactions.controller;

import com.wex.transactions.dto.TransactionDTO;
import com.wex.transactions.dto.TransactionRequest;
import com.wex.transactions.model.Transaction;
import com.wex.transactions.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
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
    public TransactionDTO create(@RequestBody TransactionRequest transactionRequest) {

        // Create a Transaction object from the request
        Transaction transaction = modelMapper.map(transactionRequest , Transaction.class);
        // Call the service to create the transaction
        Transaction createdTransaction = transactionService.save(transaction);

        return modelMapper.map(createdTransaction , TransactionDTO.class);

    }
}
