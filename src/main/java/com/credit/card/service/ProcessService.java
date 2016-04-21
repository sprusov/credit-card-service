package com.credit.card.service;

import com.credit.card.domain.CreditCard;
import com.credit.card.exception.CreditCardAlreadyExistException;
import com.credit.card.exception.CreditCardLimitExceededException;
import com.credit.card.exception.CreditCardNotFoundException;
import com.credit.card.exception.CreditCardNumberNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ProcessService
 *
 * @author Sergei Prusov
 */
@Component
public class ProcessService {

    @Autowired
    private CreditCardService creditCardService;

    /**
     * @param parameters input parameters
     */
    public CreditCard process(String... parameters) throws CreditCardLimitExceededException, CreditCardNumberNotValidException, CreditCardAlreadyExistException, CreditCardNotFoundException {
        if (parameters.length <= 4 && parameters.length >= 3) {
            CreditCard creditCard;
            switch (parameters[0].toLowerCase()) {
                case "add":
                    int param3;
                    if (parameters[3].indexOf("$") == 0) {
                        param3 = Integer.valueOf(parameters[3].substring(1, parameters[3].length()));
                    } else {
                        param3 = Integer.valueOf(parameters[3]);
                    }
                    creditCard = creditCardService.add(parameters[1], parameters[2], param3);
                    break;
                case "charge":
                    creditCard = creditCardService.charge(parameters[1], Integer.valueOf(parameters[2].substring(1, parameters[2].length())));
                    break;
                case "credit":
                    creditCard = creditCardService.credit(parameters[1], Integer.valueOf(parameters[2].substring(1, parameters[2].length())));
                    break;
                default:
                    throw new IllegalArgumentException("operation are incorrect");
            }
            return creditCard;
        } else {
            throw new IllegalArgumentException("input parameters are incorrect");
        }
    }
}