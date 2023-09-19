package com.wex.transactions.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ApiErrors {
    private List<String> errors;
    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();

        bindingResult.getAllErrors().forEach(e -> this.errors.add(e.getDefaultMessage()));
    }

    public ApiErrors(ExchangeRateProviderException ex) {
        errors = Arrays.asList(ex.getMessage());
    }

    public ApiErrors(ResponseStatusException ex) {
        System.out.println(ex.getMessage());
        errors = Arrays.asList(ex.getReason());
    }


    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
