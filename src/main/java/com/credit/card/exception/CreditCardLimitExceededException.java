package com.credit.card.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sergei Prusov
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CreditCardLimitExceededException extends RuntimeException {

    /**
     *
     * @param name credit card number
     */
    public CreditCardLimitExceededException(String name) {
        super("credit card limit exceeded for credit card: '" + name + "'");
    }
}