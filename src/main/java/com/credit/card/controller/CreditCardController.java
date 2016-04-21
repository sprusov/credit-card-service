package com.credit.card.controller;

import com.credit.card.domain.CreditCard;
import com.credit.card.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller to expose credit card service methods
 *
 * @author Sergei Prusov
 */
@RestController
@RequestMapping("api/v1/credit-card/")
public class CreditCardController {

    @Autowired
    private ProcessService processService;

    @CrossOrigin(origins = "http://locahost:8080")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody CreditCard creditCard) {
        return new ResponseEntity<>(processService.process("add", creditCard.getName(), creditCard.getNumber(), creditCard.getLimitAsString()), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://locahost:8080")
    @RequestMapping(value = "/credit", method = RequestMethod.PUT)
    public ResponseEntity credit(@RequestParam(value = "name", required = true) String name,
                                 @RequestParam(value = "amount", required = true) String amount) {
        return new ResponseEntity<>(processService.process("credit", name.trim(), amount), HttpStatus.ACCEPTED);
    }

    @CrossOrigin(origins = "http://locahost:8080")
    @RequestMapping(value = "/charge", method = RequestMethod.PUT)
    public ResponseEntity charge(@RequestParam(value = "name", required = true) String name,
                                 @RequestParam(value = "amount", required = true) String amount) {
        return new ResponseEntity<>(processService.process("charge", name.trim(), amount), HttpStatus.ACCEPTED);
    }
}