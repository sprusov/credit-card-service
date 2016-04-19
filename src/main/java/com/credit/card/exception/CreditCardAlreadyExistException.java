package com.credit.card.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sergei Prusov
 */
@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class CreditCardAlreadyExistException extends RuntimeException {

    /**
     *
     * @param ccNumber credit card number
     */
    public CreditCardAlreadyExistException(String ccNumber) {
        super("credit card already exist: '" + ccNumber + "'");
    }
}
