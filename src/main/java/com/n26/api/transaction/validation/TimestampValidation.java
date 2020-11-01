package com.n26.api.transaction.validation;

import com.n26.api.config.TransactionConfig;
import com.n26.api.transaction.data.TransactionData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class TimestampValidation implements Validation{

    @Override
    public void validate(TransactionData transactionData){
        isParsable(transactionData.getTimestamp());
        Instant timestamp = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(transactionData.getTimestamp()));
        isOld(timestamp);
        isInTheFuture(timestamp);
    }

    private void isParsable(String timestamp) {
        try{
            DateTimeFormatter.ISO_INSTANT.parse(timestamp);
        }catch (DateTimeParseException ex){
            throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private void isOld(Instant timestamp) throws ValidationException {
        if(timestamp.isBefore(Instant.now().minusSeconds(TransactionConfig.VALID_PERIOD_SECONDS))){
            throw new ValidationException(HttpStatus.NO_CONTENT);
        }
    }

    private void isInTheFuture(Instant timestamp) throws ValidationException {
        if(timestamp.isAfter(Instant.now())){
            throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
