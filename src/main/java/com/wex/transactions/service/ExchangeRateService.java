package com.wex.transactions.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wex.transactions.exceptions.ExchangeRateProviderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class ExchangeRateService implements IExchangeRateService {
    @Autowired
    private RestTemplate restTemplate;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public BigDecimal getExchangeRate(LocalDate purchaseDate, String currency) {
        try {

            LocalDate purchaseBeforeSixMonth = purchaseDate.minusMonths(6);

            String formattedDate = DATE_FORMATTER.format(purchaseBeforeSixMonth);

            // Construct the API URL with the purchaseDate and currency
            String filters = "&filter=currency:eq:" + currency + ",record_date:gte:" + formattedDate;
            String API_BASE_URL = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange";
            URI apiUrl = new URI(API_BASE_URL + "?sort=-record_date&format=json" + filters + "&page[number]=1&page[size]=1");

            // Make an HTTP GET request to the API
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

            // Parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());

            // Extract the exchange rate from the response
            JsonNode dataNode = responseJson.get("data");

            if (dataNode.isArray() && !dataNode.isEmpty()) {
                JsonNode exchangeRateNode = dataNode.get(0).get("exchange_rate");
                String effectiveDateStr = dataNode.get(0).get("effective_date").asText();
                LocalDate effectiveDate = LocalDate.parse(effectiveDateStr, DATE_FORMATTER);

                // Calculate the difference in months between effectiveDate and purchaseDate
                long monthsDifference = ChronoUnit.MONTHS.between(effectiveDate, purchaseDate);

                if (monthsDifference > 5) {
                    throw new ExchangeRateProviderException("The purchase cannot be converted to the target currency");
                }

                String exchangeRateStr = exchangeRateNode.asText();
                return new BigDecimal(exchangeRateStr).setScale(2, RoundingMode.HALF_UP);
            }

            throw new ExchangeRateProviderException("The exchange rate to this transaction date is not found");

        } catch (HttpClientErrorException | HttpServerErrorException restClientException) {
            throw new ExchangeRateProviderException("HTTP error during exchange rate request");
        } catch (ExchangeRateProviderException e) {
            throw new ExchangeRateProviderException(e.getMessage());
        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
