package com.credit.card.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sergei Prusov
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CreditCardNotFoundException extends RuntimeException {

    /**
     *
     * @param name name on credit card
     */
    public CreditCardNotFoundException(String name) {
        super("could not find credit card for name: '" + name + "'");
    }
}