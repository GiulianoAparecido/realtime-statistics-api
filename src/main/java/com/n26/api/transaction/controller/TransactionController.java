package com.n26.api.transaction.controller;

import com.n26.api.statistic.event.DeleteEvent;
import com.n26.api.statistic.event.Publisher;
import com.n26.api.statistic.event.StatisticEvent;
import com.n26.api.transaction.data.TransactionData;
import com.n26.api.transaction.validation.Validation;
import com.n26.api.transaction.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;


/**
 * Notes from Requirements:
 *
 * It does not defines what POST /transaction should perform. Usually a POST request should create a resource,
 * in this case a transaction, but as it is not defined, the resource is not created.
 * So naturally there is no link to an URI.
 */
@RestController
public class TransactionController {

    @Autowired
    private Publisher statisticPublisher;

    @Autowired
    private Validation validator;

    @PostMapping(value = "/transactions", consumes = "application/json")
    public ResponseEntity createTransaction(@RequestBody TransactionData transaction) {
        try {
            validator.validate(transaction);
        } catch (ValidationException e) {
            return ResponseEntity.status(e.getHttpStatus()).build();
        }
        updateStatistics(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/transactions")
    public ResponseEntity deleteTransactions() {
        statisticPublisher.publish(new DeleteEvent());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private void updateStatistics(TransactionData transaction) {
        StatisticEvent event = new StatisticEvent.StatisticEventBuilder()
                .amount(transaction.getAmount())
                .transactionTimestamp(transaction.getTimestamp())
                .instantNow(Instant.now())
                .build();
        statisticPublisher.publish(event);
    }
}
