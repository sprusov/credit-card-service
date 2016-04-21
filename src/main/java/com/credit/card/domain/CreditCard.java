package com.credit.card.domain;

import com.credit.card.exception.CreditCardLimitExceededException;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Domain object Credit Card
 *
 * @author @author Sergei Prusov
 */
@Entity
public class CreditCard implements Serializable, Comparable<CreditCard> {

    private int limit;
    private String number;
    private String name;
    private int balance = 0;
    private String status = "";

    @Id
    @GeneratedValue
    private Long id;

    /**
     * default constructor
     */
    public CreditCard() {
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id unique identity
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param name card holder name
     * @param number credit number
     * @param limit account limit
     */
    public CreditCard(String name, String number, int limit) {
        this.name = name;
        this.number = number;
        this.limit = limit;
    }

    /**
     * @param name name on credit card
     * @param status status of processing
     */
    public CreditCard(String name, String status) {
        this.name = name;
        this.status = status;
    }

    /**
     * @param amount amount to credit from balance
     */
    public void credit(int amount) {
        this.balance -= amount;
    }

    /**
     * @param amount amount to charge from balance
     */
    public void charge(int amount) throws CreditCardLimitExceededException{
        if (this.balance + amount > limit) {
            throw new CreditCardLimitExceededException(getName());
        } else {
            this.balance += amount;
        }
    }

    /**
     *
     * @return
     */
    public String getLimitAsString() {
        return Integer.valueOf(limit).toString();
    }

    /**
     * @return return limit of credit card
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit set limit for credit card
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return get cc number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number set cc number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return return name of credit card
     */
    public String getName() {
        return name;
    }

    /**
     * @param name set name of credit card
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return return current balance for credit card
     */
    public int getBalance() {
        return balance;
    }

    /**
     * @param balance set current balance for credit card
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * @return return status of processing
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status set status of processing
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return print status
     */
    public String printStatus() {
        return getName() + ": " + (StringUtils.isNotBlank(getStatus()) ? getStatus() : "$" + getBalance());
    }

    public static Comparator<CreditCard> CreditCardComparator = (creditCard1, creditCard2) -> {
        String fruitName1 = creditCard1.getName().toUpperCase();
        String fruitName2 = creditCard2.getName().toUpperCase();
        return fruitName1.compareTo(fruitName2);
    };

    @Override
    public int compareTo(CreditCard other) {
        return this.getName().compareTo(other.getName());
    }
}