package com.wex.transactions.service;

import com.wex.transactions.model.Transaction;
import com.wex.transactions.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        UUID fakeId = UUID.randomUUID();
        LocalDate dueDate =  LocalDate.of(2023,9,6);

        Transaction transaction = Transaction.builder()
                .description("some random description")
                .date(dueDate)
                .purchaseAmount(BigDecimal.valueOf(19.987))
                .build();

        Transaction savedTransaction = Transaction.builder()
                .id(fakeId)
                .description("some random description")
                .date(dueDate)
                .purchaseAmount(BigDecimal.valueOf(19.987))
                .build();

        when(repository.save(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction responseTransaction =  service.save(transaction);

        assertThat(responseTransaction.getId()).isNotNull();

    }

    @Test
    @DisplayName("Must retrieve a purchased transaction with amount converted")
    public void retrieveConvertedTransactionTest(){

    }
}
