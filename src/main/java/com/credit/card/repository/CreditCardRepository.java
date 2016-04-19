package com.credit.card.repository;

import com.credit.card.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This is Credit Card JPA Repository
 *
 * @author Sergei Prusov
 */
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    /**
     * @param name name on the credit card
     * @return CreditCard
     */
    CreditCard findByName(String name);
}