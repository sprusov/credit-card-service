package com.credit.card.service;

import com.credit.card.domain.CreditCard;
import com.credit.card.exception.CreditCardAlreadyExistException;
import com.credit.card.exception.CreditCardLimitExceededException;
import com.credit.card.exception.CreditCardNotFoundException;
import com.credit.card.exception.CreditCardNumberNotValidException;

/**
 * Interface for Credit Card Service
 *
 * @author Sergei Prusov
 */
public interface ICreditCardService {

    /**
     * Will create a new credit card for a given name, card number, and limit
     *
     * @param name name on the credit card
     * @param ccNumber credit card number
     * @param limit credit limit
     * @return CreditCard
     * @throws CreditCardNotFoundException, CreditCardNumberNotValidException
     */
    CreditCard add(String name, String ccNumber, int limit) throws CreditCardAlreadyExistException, CreditCardNumberNotValidException;

    /**
     * Will increase the balance of the card associated with the provided
     * name by the amount specified
     *
     * @param name name on the credit card
     * @param amount amount for charge
     * @return CreditCard
     * @throws CreditCardNotFoundException
     */
    CreditCard charge(String name, int amount) throws CreditCardLimitExceededException, CreditCardNotFoundException;

    /**
     * Will decrease the balance of the card associated with the provided
     * name by the amount specified
     *
     * @param name name on the credit card
     * @param amount amount for charge
     * @return CreditCard
     * @throws CreditCardNotFoundException
     */
    CreditCard credit(String name, int amount) throws CreditCardNotFoundException;
}