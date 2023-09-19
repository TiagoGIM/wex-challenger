package com.wex.transactions.controller;

import com.wex.transactions.model.Transaction;
import com.wex.transactions.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
                "\"transactionDate\": \"2023-09-14\", " +
                "\"purchaseAmount\": 19.99 " +
                "}";

        given(transactionService.save(any(Transaction.class)))
                .willReturn(transactionToTest());


        // Perform the POST request
        mvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) // Verify the HTTP status is 201 Created
                .andExpect(jsonPath("$.id").exists());

    }
}
