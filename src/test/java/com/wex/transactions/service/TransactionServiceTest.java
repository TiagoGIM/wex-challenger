package com.wex.transactions.service;

import com.wex.transactions.dto.TransactionResponse;
import com.wex.transactions.model.Transaction;
import com.wex.transactions.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {
    ITransactionService service;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private ModelMapper modelMapper;

    @MockBean
    TransactionRepository repository;

    // Helper method to generate a fake transaction
    private Transaction createFakeTransaction() {
        UUID fakeId = UUID.randomUUID();
        LocalDate dueDate =  LocalDate.of(2023,9,6);

        return Transaction.builder()
                .id(fakeId)
                .description("some random description")
                .date(dueDate)
                .purchaseAmount(BigDecimal.valueOf(19.987))
                .build();
    }

    @BeforeEach
    public void setUp(){

        this.service = new TransactionService(repository , exchangeRateService, modelMapper);

        when(exchangeRateService.getExchangeRate(any(LocalDate.class), anyString()))
                .thenReturn(new BigDecimal("1.23")); // Replace with the desired exchange rate
    }

    @Test
    @DisplayName("Must save a purchased transaction")
    public void saveTransactionTest(){

        Transaction transaction = createFakeTransaction();

        when(repository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction responseTransaction =  service.save(transaction);

        assertThat(responseTransaction.getId()).isNotNull();

    }

    @Test
    @DisplayName("Must retrieve a purchased transaction with amount converted")
    public void retrieveConvertedTransactionTest(){
        // Create a sample Transaction object
        UUID fakeId = UUID.randomUUID();
        LocalDate dueDate =  LocalDate.of(2023,9,6);
        Transaction transaction = Transaction.builder()
                .id(fakeId)
                .description("some random description")
                .date(dueDate)
                .purchaseAmount(BigDecimal.valueOf(19.987))
                .build();

        // Mock the exchange rate service
        when(exchangeRateService.getExchangeRate(any(LocalDate.class), anyString()))
                .thenReturn(new BigDecimal("4.95"));

        // Mock the repository to return the Transaction
        when(repository.findById(transaction.getId())).thenReturn(Optional.of(transaction));

        // Call the method under test
        Optional<TransactionResponse> result = service.convertedTransaction(transaction.getId(), "USD");

        // Verify the result
        assertTrue(result.isPresent());
        TransactionResponse response = result.get();
        assertEquals(transaction.getId(), response.getId());
        assertEquals(transaction.getDate(), response.getDate());
        assertEquals(transaction.getPurchaseAmount(), response.getPurchaseAmount());
        assertEquals(new BigDecimal("98.94"), response.getConvertedAmount());
        assertEquals(new BigDecimal("4.95"), response.getExchangeRate());
    }

}
