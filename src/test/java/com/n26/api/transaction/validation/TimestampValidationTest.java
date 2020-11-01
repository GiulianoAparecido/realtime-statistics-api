package com.n26.api.transaction.validation;

import com.n26.api.config.TransactionConfig;
import com.n26.api.transaction.data.TransactionData;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public class TimestampValidationTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void throwExceptionWith422WhenNotParsable(){
        Validation sut = new TimestampValidation();
        TransactionData notParsableTimestamp = new TransactionData.TransactionDataBuilder().timestamp("not_parsable").build();

        new ValidationExceptionAssertion()
                .expectedHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                .assertException(sut::validate, notParsableTimestamp);
    }

    @Test
    public void throwExceptionWith204WhenOld(){
        Validation sut = new TimestampValidation();
        TransactionData oldTimestamp = new TransactionData.TransactionDataBuilder().timestamp(oldTimestamp()).build();

        new ValidationExceptionAssertion()
                .expectedHttpStatus(HttpStatus.NO_CONTENT)
                .assertException(sut::validate, oldTimestamp);
    }

    @Test
    public void throwExceptionWith422WhenFuture(){
        Validation sut = new TimestampValidation();
        TransactionData futureTimestamp = new TransactionData.TransactionDataBuilder().timestamp(futureTimestamp()).build();

        new ValidationExceptionAssertion()
                .expectedHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                .assertException(sut::validate, futureTimestamp);
    }

    @Test
    public void throwNoExceptionWhenNearPast(){
        Validation sut = new TimestampValidation();
        TransactionData nearPastTimestamp = new TransactionData.TransactionDataBuilder().timestamp(nearPastTimestamp()).build();

        sut.validate(nearPastTimestamp);
    }

    private static String oldTimestamp(){
        return Instant.now().minusSeconds(TransactionConfig.VALID_PERIOD_SECONDS + 1000).toString();
    }

    private static String futureTimestamp(){
        return Instant.now().plusSeconds(TransactionConfig.VALID_PERIOD_SECONDS).toString();
    }

    private static String nearPastTimestamp(){
        return Instant.now().minusSeconds(1).toString();
    }
}
