package com.wex.transactions.exceptions;

import java.io.Serial;

public class ExchangeRateProviderException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public ExchangeRateProviderException(String msg) {
        super(msg);
    }

}
