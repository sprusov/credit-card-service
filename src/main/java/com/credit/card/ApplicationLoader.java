package com.credit.card;

import com.credit.card.domain.CreditCard;
import com.credit.card.exception.CreditCardAlreadyExistException;
import com.credit.card.exception.CreditCardLimitExceededException;
import com.credit.card.exception.CreditCardNotFoundException;
import com.credit.card.exception.CreditCardNumberNotValidException;
import com.credit.card.service.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Sergei Prusov
 */
@Component
public class ApplicationLoader implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationLoader.class);

    @Autowired
    private ProcessService processService;

    @Value("${enable-scan}")
    private boolean enableScan;

    @Override
    public void run(String... args) throws Exception {

        StringBuilder sb = new StringBuilder();

        for (String option : args) {
            sb.append(option);
            if (sb.length() > 1) {
                sb.append(" ");
            }
        }

        sb = sb.length() == 0 ? sb.append("No Options Specified") : sb;

        LOG.info(String.format("Application launched with following options: %s", sb.toString()));

        String[] parameters = sb.toString().split(" ");

        switch (parameters.length) {
            case 1:
                try {
                    List<CreditCard> result = new ArrayList<>();
                    InputStream inStreamObject = new FileInputStream(parameters[0]);
                    Scanner scan = new Scanner(inStreamObject);

                    LOG.info("Processing...");

                    while (scan.hasNext()) {

                        String line = scan.nextLine();
                        parameters = line.split(" ");

                        try {
                            result.add(processService.process(parameters));
                        } catch (CreditCardLimitExceededException | CreditCardNumberNotValidException | CreditCardAlreadyExistException | CreditCardNotFoundException e) {
                            result.add(new CreditCard(parameters[1], "error"));
                        }
                    }

                    Collections.sort(result, CreditCard.CreditCardComparator);

                    LOG.info("Result:");
                    for (CreditCard creditCard : result) {
                        LOG.info(creditCard.printStatus());
                    }
                } catch (FileNotFoundException e) {
                    LOG.error(String.format("Exception occurred during file read operation: %s", e.getLocalizedMessage()));
                }
                break;
            default:
                while (enableScan) {
                    Scanner scanIn = new Scanner(System.in);
                    LOG.info(processService.process(scanIn.nextLine().split(" ")).printStatus());
                }
        }
    }
}