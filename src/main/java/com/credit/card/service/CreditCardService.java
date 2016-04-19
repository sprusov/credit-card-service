package com.credit.card.service;

import com.credit.card.domain.CreditCard;
import com.credit.card.exception.CreditCardAlreadyExistException;
import com.credit.card.exception.CreditCardLimitExceededException;
import com.credit.card.exception.CreditCardNotFoundException;
import com.credit.card.exception.CreditCardNumberNotValidException;
import com.credit.card.helper.CreditCardHelper;
import com.credit.card.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is the 'business logic' layer, where business logic is implemented
 *
 * @author @author Sergei Prusov
 */
@Service
public class CreditCardService implements ICreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    /**
     * @param name card holder name
     * @param ccNumber secret code
     * @param limit account limit
     * @throws CreditCardNotFoundException, CreditCardNumberNotValidException
     */
    public CreditCard add(String name, String ccNumber, int limit) throws CreditCardAlreadyExistException, CreditCardNumberNotValidException {
        if (CreditCardHelper.isValid(ccNumber)) {
            if (creditCardRepository.findByName(name) != null) {
                throw new CreditCardAlreadyExistException(ccNumber);
            } else {
                CreditCard creditCard = new CreditCard(name, ccNumber, limit);
                return creditCardRepository.save(creditCard);
            }
        } else {
            throw new CreditCardNumberNotValidException(ccNumber);
        }
    }

    /**
     * @param name card holder name
     * @param amount amount to charge
     * @throws CreditCardNotFoundException
     */
    public CreditCard charge(String name, int amount) throws CreditCardLimitExceededException, CreditCardNotFoundException {
        CreditCard creditCard = creditCardRepository.findByName(name);
        if (creditCard == null) {
            throw new CreditCardNotFoundException(name);
        } else {
            creditCard.charge(amount);
            return creditCardRepository.save(creditCard);
        }
    }

    /**
     * @param name card holder name
     * @param amount amount to credit
     * @throws CreditCardNotFoundException
     */
    public CreditCard credit(String name, int amount) throws CreditCardNotFoundException {
        CreditCard creditCard = creditCardRepository.findByName(name);
        if (creditCard == null) {
            throw new CreditCardNotFoundException(name);
        } else {
            creditCard.credit(amount);
            return creditCardRepository.save(creditCard);
        }
    }
}