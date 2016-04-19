package com.credit.card.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sergei Prusov
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CreditCardNumberNotValidException extends RuntimeException {

    /**
     *
     * @param ccNumber credit card number
     */
    public CreditCardNumberNotValidException(String ccNumber) {
        super("credit card number not valid: '" + ccNumber + "'");
    }
}