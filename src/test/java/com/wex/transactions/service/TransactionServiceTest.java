package com.wex.transactions.service;

import com.wex.transactions.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {
    ITransactionService service;

    @MockBean
    TransactionRepository repository;

    @BeforeEach
    public void setUp(){
        this.service = new TransactionService(repository);
    }

    @Test
    @DisplayName("Must save a purchased transaction")
    public void saveTransactionTest(){

    }

    @Test
    @DisplayName("Must retrieve a purchased transaction with amount converted")
    public void retrieveConvertedTransactionTest(){

    }
}
