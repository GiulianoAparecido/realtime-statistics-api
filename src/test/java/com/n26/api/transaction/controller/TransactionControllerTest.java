package com.n26.api.transaction.controller;

import com.n26.Application;
import com.n26.api.statistic.event.DeleteEvent;
import com.n26.api.statistic.event.Event;
import com.n26.api.statistic.event.Publisher;
import com.n26.api.statistic.event.StatisticEvent;
import com.n26.api.transaction.data.TransactionData;
import com.n26.api.transaction.validation.Validation;
import com.n26.api.transaction.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionControllerTest {

    @Autowired
    private Publisher statisticPublisher;

    @Autowired
    private Validation validator;

    @Autowired
    private TransactionController transactionController;

    @Test
    public void failsWhenValidationFailsInPost(){
        TransactionData anyTransaction = new TransactionData.TransactionDataBuilder()
                .amount("123.45")
                .timestamp("2020-11-01T09:21:20.712Z")
                .build();
        HttpStatus expectedHttpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        Mockito.doThrow(new ValidationException(expectedHttpStatus)).when(validator).validate(anyTransaction);

        ResponseEntity response = transactionController.createTransaction(anyTransaction);

        assertEquals(expectedHttpStatus, response.getStatusCode());
    }

    @Test
    public void publishStatisticsWhenPost(){
        TransactionData givenTransaction = new TransactionData.TransactionDataBuilder()
                .amount("123.45")
                .timestamp("2020-11-01T09:21:20.712Z")
                .build();

        transactionController.createTransaction(givenTransaction);

        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        Mockito.verify(statisticPublisher).publish(argumentCaptor.capture());
        StatisticEvent publishedEvent = (StatisticEvent)argumentCaptor.getValue();
        assertEquals(publishedEvent.getAmount(), givenTransaction.getAmount());
        assertEquals(publishedEvent.getTransactionTimestamp(), givenTransaction.getTimestamp());
    }

    @Test
    public void publishStatisticsWhenDelete(){
        transactionController.deleteTransactions();

        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        Mockito.verify(statisticPublisher).publish(argumentCaptor.capture());
        assertTrue(argumentCaptor.getValue() instanceof DeleteEvent);
    }

    @Before
    public void clearMockInvocations(){
        Mockito.clearInvocations(statisticPublisher);
    }
}
