package com.credit.card.helper;

import org.apache.commons.lang.StringUtils;

/**
 * Validate credit card number is valid or not based on Luhn 10 algorithm
 */
public class CreditCardHelper {

    /**
     * Validate credit card number is valid or not based on Luhn 10 algorithm
     *
     * @param ccNumber credit card number
     * @return boolean is valid luhn 10 or not
     */
    public static boolean isValid(String ccNumber) throws IllegalArgumentException {

        if (StringUtils.isBlank(ccNumber)) {
            throw new IllegalArgumentException("Credit card number must not be blank");
        }
        if (ccNumber.length() > 20) {
            throw new IllegalArgumentException("Card number may not be more than 19 digits long");
        }
        try {
            Long number = Long.parseLong(ccNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Card number must contain only digits");
        }


        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}