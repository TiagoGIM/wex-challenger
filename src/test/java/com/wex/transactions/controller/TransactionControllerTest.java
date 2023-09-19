package com.wex.transactions.controller;

import com.wex.transactions.dto.TransactionResponse;
import com.wex.transactions.model.Transaction;
import com.wex.transactions.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc
public class TransactionControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;

    @InjectMocks
    private ModelMapper modelMapper;

    @InjectMocks
    private TransactionController controller;


    public Transaction transactionToTest() {
        UUID fakeId = UUID.randomUUID();

        LocalDate dueDate =  LocalDate.of(2023,9,6);

        return Transaction.builder()
                .id(fakeId)
                .description("some random description")
                .date(dueDate)
                .purchaseAmount(BigDecimal.valueOf(19.987))
                .build();
    }
    @Test
    @DisplayName("Must save a new purchase transaction")
    public void testCreateTransaction() throws Exception {
        String requestBody = "{ " +
                "\"description\": \"Sample Transaction\", " +
                "\"date\": \"2023-09-14\", " +
                "\"purchaseAmount\": 19.99 " +
                "}";

        given(transactionService.save(any(Transaction.class)))
                .willReturn(transactionToTest());


        // Perform the POST request
        mvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

    }

    @Test
    @DisplayName("Must return a transaction with all fields from transactionResponse")
    public void testConvertedPurchase() throws Exception {
        // Create a fake UUID and currency
        UUID fakeId = UUID.randomUUID();
        String currency = "USD";


        // Create a fake transaction response
        TransactionResponse fakeTransactionResponse = TransactionResponse.builder()
                .id(fakeId)
                .description("Sample Transaction")
                .date(LocalDate.of(2023, 9, 6))
                .purchaseAmount(BigDecimal.valueOf(19.87))
                .convertedAmount(BigDecimal.valueOf(23.45))
                .exchangeRate(BigDecimal.valueOf(1.18))
                .build();

        // Mock the behavior of the transactionService
        when(transactionService.convertedTransaction(fakeId, currency))
                .thenReturn(Optional.of(fakeTransactionResponse));

        // Perform the GET request to the endpoint
        mvc.perform(MockMvcRequestBuilders
                        .get("/transactions/convert/{currency}/{id}", currency, fakeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Id").value(fakeId.toString()))
                .andExpect(jsonPath("$.description").value("Sample Transaction"))
                .andExpect(jsonPath("$.date[0]").value(2023))
                .andExpect(jsonPath("$.date[1]").value(9))
                .andExpect(jsonPath("$.date[2]").value(6))
                .andExpect(jsonPath("$.purchaseAmount").value("19.87"))
                .andExpect(jsonPath("$.convertedAmount").value("23.45"))
                .andExpect(jsonPath("$.exchangeRate").value("1.18"));
    }

}
